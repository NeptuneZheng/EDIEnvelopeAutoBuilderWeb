package hadoop.util

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FSDataInputStream
import org.apache.hadoop.fs.FSDataOutputStream
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.IOUtils
import org.apache.log4j.Logger

class HadoopUtil {
    private static Logger hlogger = Logger.getLogger(HadoopUtil.class)

    public static final String HADOOP_PATH = "hdfs://192.168.47.128:9000";
    public static String data_center_path = "/usr/local/hadoop/tmp/dfs/data/"
    private static Configuration cnf = new Configuration()
    private static FileSystem fs = FileSystem.get(new URI(HADOOP_PATH),cnf)

    //create
    /**
     * @author Neptune Zheng
     * @date 2018-05-03 10:14
     * @param dir_path
     * @return
     * @throws
     * @description
    */
    public static void CREATE_FOLDER(String dir_path){
        fs.mkdirs(new Path(data_center_path + dir_path))
        hlogger.info("CREATE_FOLDER: " + data_center_path + dir_path)
    }

    //upload data
    /**
     * @author Neptune Zheng
     * @date 2018-05-03 10:15
     * @param filePath, targetPath
     * @return
     * @throws
     * @description
    */
    public static boolean UPLOAD(String filePath, String targetPath){
        boolean upd_sts = true
        FSDataOutputStream out = fs.create(new Path(targetPath))
        FileInputStream ins = new FileInputStream(new File(filePath))
        try {
            IOUtils.copyBytes(ins,out,1024,true)
            hlogger.info("UPLOAD" + filePath + " to " + targetPath + " Sucess.")
        }catch (Exception e){
            hlogger.error("UPLOAD Fail, fail detials: \r\n" + e.toString())
        }finally{
            return upd_sts
        }
    }

    //download data
    public static DOWNLOAD_DATA(String hdfs_path, String save_path){
        FSDataInputStream ins = fs.open(new Path(hdfs_path))
        if(!save_path){
            IOUtils.copyBytes(ins,System.out,1024,true)
            hlogger.info("DOWNLOAD_DATA reade data of: " + hdfs_path)
        }else{
            IOUtils.copyBytes(ins,new FileOutputStream(new File(save_path)),1024,true)
            hlogger.info("DOWNLOAD_DATA from: " + hdfs_path + " to: " + save_path)
        }
    }

    //remove data
    public static REMOVE_DATA(String file_path){
        fs.delete(new Path(file_path),true)
    }

}
