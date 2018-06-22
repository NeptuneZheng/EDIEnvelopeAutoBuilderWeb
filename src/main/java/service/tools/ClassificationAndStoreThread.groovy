package service.tools

import dao.BRMigrationDao
import dao.IDCollectionDao
import hadoop.util.HadoopUtil
import org.apache.log4j.Logger
import pojo.BRMigrationFileSystem

class ClassificationAndStoreThread extends Thread {
    private static BRMigrationDao brDao;
    private static IDCollectionDao idDao;
    private static hadoop_data_center_path = '/usr/local/hadoop/tmp/dfs/data/Carrier BR/'
    public static Logger logger = Logger.getLogger(ClassificationAndStoreThread.class);
    private static Object lock = new Object()

    //File fis, String outputPath, int start_idx, String tp_id, String data_version, BufferedReader bufferedReader
    private String tp_id
    private String data_version
    private String outputPath
    private File fis
    private BufferedReader bufferedReader
    private int thread_id

    private static int thread_counter;

    public ClassificationAndStoreThread(File fis, String outputPath, String tp_id, String data_version, BufferedReader bufferedReader, int thread_id){
        this.tp_id = tp_id
        this.data_version = data_version
        this.outputPath = outputPath
        this.fis = fis
        this.bufferedReader = bufferedReader
        this.thread_id = thread_id
    }

    public void run(){
        logger.info("ClassificationAndStoreThread init new thread: " + 'RunningThread_' + thread_id)

        try {

            for (File file : fis.listFiles()) {
                synchronized (lock) {
                    if (file.isDirectory()) {
                        logger.info("Can't distribute file folder !")
                    } else if (file.isFile()) {
                        String output = outputPath;
                        String sub_hadoop_path = ''
                        System.out.println("Working info: " + thread_id + "-" + BRMigrationFileHandler.start_idx + "-" + file.getName());
                        String oldName = file.getName();
                        // gen new name for all data, data_version format suggest use 'yyyyMMdd'
                        String newName = BRMigrationFileHandler.start_idx + "_" + tp_id + "_" + data_version + ".edi";
                        String csBookingNum = "";
                        String msg_type = "";
                        String actionType = "";
                        try {
                            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                            String lineTxt = null;
                            int idx = 1;
                            String prime = "";
                            String prime_a = "";
                            String replacer = "";
                            String replacer_a = "";

                            while ((lineTxt = bufferedReader.readLine()) != null) {
                                /*use 1st line to check input data type,eg:
                                 * EDI: start with ISA
                                 * XML: start with <?xml
                                 * UIF: else
                                 */
                                if (idx == 1) {
                                    if (lineTxt.startsWith("ISA")) {
                                        prime = "N9*ZZ*";
                                        prime_a = "B1*";
                                        replacer = ".*N9\\*ZZ\\*";
                                        replacer_a = "(.*B1\\*(.*?\\*){3})|~.*";
                                        msg_type = "EDI";
                                        output += "/O_EDI/";
                                        sub_hadoop_path += "O_EDI/";
                                    } else if (lineTxt.startsWith("<?xml")) {
                                        prime = "<CSBookingRefNumber>";
                                        prime_a = "<ActionType>";
                                        replacer = "<CSBookingRefNumber>|</CSBookingRefNumber>";
                                        replacer_a = "<ActionType>|</ActionType>";
                                        msg_type = "XML";
                                        output += "/O_XML/";
                                        sub_hadoop_path += "O_XML/";
                                    } else if (lineTxt.length() > 0) {
                                        prime = "EXTERNAL REF   70";
                                        prime_a = "ACTION         ";
                                        replacer = "EXTERNAL REF   70";
                                        replacer_a = "ACTION         ";
                                        msg_type = "UIF";
                                        output += "/O_UIF/";
                                        sub_hadoop_path += "O_UIF/";
                                    }
                                    idx++;
                                }
                                int break_point = 0;
                                if (lineTxt.contains(prime) && prime.length() > 0) {
                                    // get current CSBookingRefNumber
                                    csBookingNum = lineTxt.trim().replaceAll(replacer, "").replaceAll("~.*", "");
                                    break_point++;
                                }
                                if (lineTxt.contains(prime_a) && prime_a.length() > 0) {
                                    def action_type_list = ['N': 'REQ', 'U': 'UPD', 'D': 'CAN', 'NEW': 'REQ', 'CANCEL': 'CAN', 'UPD': 'UPD', 'REQ': 'REQ', 'CAN': 'CAN']
                                    // get current action type
                                    actionType = action_type_list.get(lineTxt.trim().replaceAll(replacer_a, ""))
                                    break_point++;
                                }
                                if (break_point > 1) {
                                    break;
                                }
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                if (!csBookingNum.equals("") || (csBookingNum.equals("") && actionType == "CAN")) {
                                    // select DB to decide if should insert or update
                                    String file_update_name = "";
//                                BRMigrationFileSystem brObj = new BRMigrationFileSystem()
                                    BRMigrationFileSystem brObj = brDao.findOneBRDataRecordBycsBookingNumAndActionType(csBookingNum, actionType);
                                    if (brObj == null) {
                                        // insert new record
                                        brObj = new BRMigrationFileSystem();
                                        brObj.setId(BRMigrationFileHandler.start_idx);
                                        brObj.setN_input_file_name(newName);
                                        if (msg_type.equals("EDI")) {
                                            brObj.setProd_EDI_name(oldName);
                                        } else if (msg_type.equals("XML")) {
                                            brObj.setO_input_file_name(oldName);
                                        } else if (msg_type.equals("UIF")) {
                                            brObj.setProd_UIF_name(oldName);
                                        }
                                        brObj.setcsBookingRefNumber(csBookingNum);
                                        brObj.setData_version(data_version);
                                        brObj.setCreate_date(new Date());
                                        brObj.setUpdate_date(new Date());
                                        brObj.setAction_type(actionType)
                                        brObj.setDuplicate_flag("N");
                                        brObj.setMatch_flag("N");
                                    } else if (msg_type.equals("EDI")) {
                                        if (brObj.getProd_EDI_name() == null) {
                                            file_update_name = brObj.getN_input_file_name();
                                            brObj.setProd_EDI_name(oldName);
                                            brObj.setUpdate_date(new Date());
                                            brObj.setMatch_flag(brObj.getMatch_flag().replaceAll("N", "Y") + "Y");
                                        } else {
                                            // if exist duplicate, add D to current new file name and update previous record Duplicate_flag to Y
                                            brObj.setDuplicate_flag("Y");
                                            brDao.saveNewRecord(brObj);
                                            newName += "_D";
                                            brObj.setId(BRMigrationFileHandler.start_idx);
                                            brObj.setN_input_file_name(newName);
                                            brObj.setProd_EDI_name(oldName);
                                            brObj.setData_version(data_version);
                                            brObj.setCreate_date(new Date());
                                            brObj.setUpdate_date(new Date());
                                        }
                                    } else if (msg_type.equals("XML")) {
                                        if (brObj.getO_input_file_name() == null) {
                                            file_update_name = brObj.getN_input_file_name();
                                            brObj.setO_input_file_name(oldName);
                                            brObj.setUpdate_date(new Date());
                                            brObj.setMatch_flag(brObj.getMatch_flag().replaceAll("N", "Y") + "Y");
                                        } else {
                                            // if exist duplicate, add D to current new file name and update previous record Duplicate_flag to Y
                                            brObj.setDuplicate_flag("Y");
                                            brDao.saveNewRecord(brObj);
                                            newName += "_D";
                                            brObj.setId(BRMigrationFileHandler.start_idx);
                                            brObj.setN_input_file_name(newName);
                                            brObj.setO_input_file_name(oldName);
                                            brObj.setData_version(data_version);
                                            brObj.setCreate_date(new Date());
                                            brObj.setUpdate_date(new Date());
                                        }
                                    } else if (msg_type.equals("UIF")) {
                                        if (brObj.getProd_UIF_name() == null) {
                                            file_update_name = brObj.getN_input_file_name();
                                            brObj.setProd_UIF_name(oldName);
                                            brObj.setUpdate_date(new Date());
                                            brObj.setMatch_flag(brObj.getMatch_flag().replaceAll("N", "Y") + "Y");
                                        } else {
                                            // if exist duplicate, add D to current new file name and update previous record Duplicate_flag to Y
                                            brObj.setDuplicate_flag("Y");
                                            brDao.saveNewRecord(brObj);
                                            newName += "_D";
                                            brObj.setId(BRMigrationFileHandler.start_idx);
                                            brObj.setN_input_file_name(newName);
                                            brObj.setProd_UIF_name(oldName);
                                            brObj.setData_version(data_version);
                                            brObj.setCreate_date(new Date());
                                            brObj.setUpdate_date(new Date());

                                        }
                                    }
                                    boolean idx_add_flag = true;
                                    if (!file_update_name.equals("")) {
                                        newName = file_update_name;
                                        idx_add_flag = false;
                                    }
                                    // update file name to new file name
                                    final boolean a = file.renameTo(new File(output + newName));
                                    if (a) {
                                        //add to hadoop data center
                                        HadoopUtil.UPLOAD(output + newName, hadoop_data_center_path + sub_hadoop_path + newName)
                                        // insert or update record
                                        brDao.saveNewRecord(brObj);
                                        if (idx_add_flag) {
                                            BRMigrationFileHandler.start_idx++;
                                        }
                                        logger.info("Name Updated Sucess From " + oldName + "###" + oldName + "---------->" + newName)
                                    } else {
                                        logger.warn("Name Updated Fail !!!   ::" + oldName + " to new name: :" + newName)
                                        System.exit(1);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error('ClassificationAndStoreThread error: ' + e.toString())
        }finally{
           thread_counter --
        }


    }

    public static void startThreadService(int thread_counter, BRMigrationDao brDao, IDCollectionDao idDao){
        this.thread_counter = thread_counter
        this.brDao = brDao
        this.idDao = idDao
        logger.info("ClassificationAndStoreThread start thread service, service number is " + thread_counter)
    }

    public static void stopThreadService(){
        while (this.thread_counter > 0){
            logger.info("ClassificationAndStoreThread still exists" + thread_counter + " thread running, pls wait.")
            Thread.sleep(1000)
        }
        logger.info("ClassificationAndStoreThread stop all thread service.")
    }
}
