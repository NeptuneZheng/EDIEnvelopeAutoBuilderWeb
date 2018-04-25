package service.tools;

import dao.BRMigrationDao;
import dao.IDCollectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.BRMigrationFileSystem;
import pojo.IDCollection;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
  @ main function are copied from FileDistribution class
 */

@Service("BRMigrationFileHandler")
public class BRMigrationFileHandler {
    private final BRMigrationDao brDao;
    private final IDCollectionDao idDao;

    @Autowired
    public BRMigrationFileHandler(BRMigrationDao dao, IDCollectionDao idDao){
        this.brDao = dao;
        this.idDao = idDao;
    }

    public void distributeFileAndRecord(String inputPath, String outputPath, String tp_id, String data_version){
        //get the latest max id, if none, start from 0
        int start_idx = idDao.getLastMaxId("BRMig");
        if(start_idx < 1){
            start_idx = 0;
        }
        // I/O setup part
        BufferedReader bufferedReader = null;
        File fis = new File(inputPath);
        // in order to auto distribute file to corresponding path, need create folder first
        File ofile = new File(outputPath);
        List<String> foldeList = new ArrayList<String>();
        foldeList.add("O_EDI");
        foldeList.add("O_UIF");
        foldeList.add("O_XML");
        // check if folder already exist
        for(File f:ofile.listFiles())
            if (f.isDirectory() && foldeList.contains(f.getName())) {
                System.out.println("Reuse output folder: " + f.getPath());
                foldeList.remove(f.getName());
            }
        for(String s : foldeList){
            File folder=new File(outputPath+"/"+s);
            folder.mkdirs();
        }
        String key = "";
        boolean distributeFlag = false;

        // read file part
        for(File file : fis.listFiles()){
            if(file.isDirectory()){
                System.out.println("Can't distribute file folder !");
            }else if(file.isFile()){
                String output = outputPath;
                System.out.println(file.getName());
                String oldName = file.getName();
                // gen new name for all data, data_version format suggest use 'yyyyMMdd'
                String newName = start_idx+"_"+tp_id+"_"+data_version+".edi";
                String csBookingNum = "";
                String msg_type = "";
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String lineTxt = null;
                    int idx = 1;
                    String prime = "";
                    String replacer = "";
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        /*use 1st line to check input data type,eg:
                         * EDI: start with ISA
                         * XML: start with <?xml
                         * UIF: else
                         */
                        if(idx == 1){
                            if(lineTxt.startsWith("ISA")){
                                prime = "N9*ZZ*";
                                replacer = ".*N9\\*ZZ\\*";
                                msg_type = "EDI";
                                output += "/O_EDI/";
                            }else if(lineTxt.startsWith("<?xml")){
                                prime = "<CSBookingRefNumber>";
                                replacer = "<CSBookingRefNumber>|</CSBookingRefNumber>";
                                msg_type = "XML";
                                output += "/O_XML/";
                            }else if(lineTxt.length() > 0){
                                prime = "EXTERNAL REF   70";
                                replacer = "EXTERNAL REF   70";
                                msg_type = "UIF";
                                output += "/O_UIF/";
                            }
                            idx ++;
                        }
                        if(lineTxt.contains(prime) && prime.length() > 0){
                            // get current CSBookingRefNumber
                            csBookingNum = lineTxt.trim().replaceAll(replacer,"").replaceAll("~.*","");
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        if(!csBookingNum.equals("")){
                            // select DB to decide if should insert or update
                            String file_update_name = "";
                            BRMigrationFileSystem brObj = brDao.findOneBRDataRecord(csBookingNum);
                            if(brObj == null){
                                // insert new record
                                brObj = new BRMigrationFileSystem();
                                brObj.setId(start_idx);
                                brObj.setN_input_file_name(newName);
                                if(msg_type.equals("EDI")){
                                    brObj.setProd_EDI_name(oldName);
                                }else if(msg_type.equals("XML")){
                                    brObj.setO_input_file_name(oldName);
                                }else if(msg_type.equals("UIF")){
                                    brObj.setProd_UIF_name(oldName);
                                }
                                brObj.setcsBookingRefNumber(csBookingNum);
                                brObj.setData_version(data_version);
                                brObj.setCreate_date(new Date());
                                brObj.setUpdate_date(new Date());
                                brObj.setDuplicate_flag("N");
                                brObj.setMatch_flag("N");
                            }else if(msg_type.equals("EDI")){
                                if( brObj.getProd_EDI_name() == null){
                                    file_update_name = brObj.getN_input_file_name();
                                    brObj.setProd_EDI_name(oldName);
                                    brObj.setUpdate_date(new Date());
                                    brObj.setMatch_flag(brObj.getMatch_flag().replaceAll("N","Y")+"Y");
                                }else{
                                    // if exist duplicate, add D to current new file name and update previous record Duplicate_flag to Y
                                    brObj.setDuplicate_flag("Y");
                                    brDao.saveNewRecord(brObj);
                                    newName += "_D";
                                    brObj.setId(start_idx);
                                    brObj.setN_input_file_name(newName);
                                    brObj.setProd_EDI_name(oldName);
                                    brObj.setData_version(data_version);
                                    brObj.setCreate_date(new Date());
                                    brObj.setUpdate_date(new Date());
                                }
                            }else if(msg_type.equals("XML")){
                                if( brObj.getO_input_file_name()== null){
                                    file_update_name = brObj.getN_input_file_name();
                                    brObj.setO_input_file_name(oldName);
                                    brObj.setUpdate_date(new Date());
                                    brObj.setMatch_flag(brObj.getMatch_flag().replaceAll("N","Y")+"Y");
                                }else{
                                    // if exist duplicate, add D to current new file name and update previous record Duplicate_flag to Y
                                    brObj.setDuplicate_flag("Y");
                                    brDao.saveNewRecord(brObj);
                                    newName += "_D";
                                    brObj.setId(start_idx);
                                    brObj.setN_input_file_name(newName);
                                    brObj.setO_input_file_name(oldName);
                                    brObj.setData_version(data_version);
                                    brObj.setCreate_date(new Date());
                                    brObj.setUpdate_date(new Date());
                                }
                            }else if(msg_type.equals("UIF")){
                                if( brObj.getProd_UIF_name()== null){
                                    file_update_name = brObj.getN_input_file_name();
                                    brObj.setProd_UIF_name(oldName);
                                    brObj.setUpdate_date(new Date());
                                    brObj.setMatch_flag(brObj.getMatch_flag().replaceAll("N","Y")+"Y");
                                }else{
                                    // if exist duplicate, add D to current new file name and update previous record Duplicate_flag to Y
                                    brObj.setDuplicate_flag("Y");
                                    brDao.saveNewRecord(brObj);
                                    newName += "_D";
                                    brObj.setId(start_idx);
                                    brObj.setN_input_file_name(newName);
                                    brObj.setProd_UIF_name(oldName);
                                    brObj.setData_version(data_version);
                                    brObj.setCreate_date(new Date());
                                    brObj.setUpdate_date(new Date());

                                }
                            }
                            boolean idx_add_flag = true;
                            if(!file_update_name.equals("")){
                                newName = file_update_name;
                                idx_add_flag = false;
                            }
                            // update file name to new file name
                            final boolean a = file.renameTo(new File(output+newName));
                            if(a){
                                // insert or update record
                                brDao.saveNewRecord(brObj);
                                if(idx_add_flag){
                                    start_idx ++;
                                }
                                System.out.println("Name Updated Sucess From "+oldName+"###"+oldName+"---------->"+newName);
                            }else{
                                System.out.println("Name Updated Fail !!!   ::"+oldName);
                                System.exit(1);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                continue;
            }
        }
        // update max idx
        idDao.updateMaxId(new IDCollection("BRMig",start_idx));
    }

    public void selectFile(String dataPath,List<String> file_name_list){
        File file_s = new File(dataPath);
        if(file_name_list.size() > 0){
            File out_folder = new File(dataPath + "/" + new Date().getMinutes() + "min_Select");
            out_folder.mkdir();
            for(File f: file_s.listFiles()){
                if(f.isDirectory()){
                    System.out.println("Can't distribute file folder1 !");
                }else {
                    String s_file_name = f.getName();
                    for(String r_file_name: file_name_list){
                        if(r_file_name.equals(s_file_name)){
                            final boolean a = f.renameTo(new File(f.getPath().replaceAll(s_file_name,out_folder.getName()+"/"+s_file_name)));
                            if(a){
                                System.out.println("Select sucess ~"+"***********"+s_file_name);
                            }else{
                                System.out.println("Select fail ~"+"-----------"+s_file_name);
                            }
                        }
                    }
                }
            }
        }else {
            System.out.println("file_name_list size less than 1~");
        }
    }

    public List<String> getSelectedFileNameList(List<BRMigrationFileSystem> brs){
        List<String> new_names = new ArrayList<String>();
        for(BRMigrationFileSystem br:brs){
            if(br.getN_input_file_name() != null){
                new_names.add(br.getN_input_file_name());
            }
        }
        return new_names;
    }

    public List<BRMigrationFileSystem> GET_NONE_DUPLICATE_AND_MATCHED_LIST(String dataVersion){
       return brDao.findMatchButNotDuplicateRecord(dataVersion);
    }

    public void selectDataSet(String folderPath,List<String> file_name_list){
        File folder = new File(folderPath);
        for(File f : folder.listFiles()){
            if(f.isDirectory()){
                selectFile(f.getPath(),file_name_list);
            }else if(f.isFile()){
                selectFile(folderPath,file_name_list);
            }
        }
    }
}
