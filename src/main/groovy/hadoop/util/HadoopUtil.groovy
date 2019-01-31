package hadoop.util

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FSDataInputStream
import org.apache.hadoop.fs.FSDataOutputStream
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.IOUtils
import org.apache.hadoop.util.Progressable
import org.apache.log4j.Logger

import java.text.DecimalFormat

class HadoopUtil {
    private static Logger hlogger = Logger.getLogger(HadoopUtil.class)
    private static DecimalFormat df = new DecimalFormat('0.00')

    public static final String HADOOP_PATH = "hdfs://192.168.47.129:9000";
    public static String data_center_path = "/usr/local/hadoop/tmp/dfs/data/"
    private static Configuration cnf = new Configuration()
    //此处的user对应的Linux虚拟机中能够有权限操纵hadoop的用户名，这样在进行权限验证的时候就能通过；
    //还有另外一种解决方案就是把HADOOP_HOME 在Windows里面设置为全局变量，即HADOOP_HOME = ‘hadoop’
    private static FileSystem fs = FileSystem.get(new URI(HADOOP_PATH),cnf,"hadoop")

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
        File file = new File(filePath)
        println('total file size: ' + df.format(file.length()/Math.pow(2,20))+'M')
        def upload_sub_count = 0
        FSDataOutputStream out = fs.create(new Path(targetPath),new Progressable() {
            @Override
            // 显示文件上传进度
            void progress() {
                println('upload progress: ' + df.format(upload_sub_count*1024*100/file.length()) + '%')
                upload_sub_count ++
            }
        })
        FileInputStream ins = new FileInputStream(file)
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

    //below samples are another solution to connect to hadoop by HTTP call
    public InputStream connectToHDFS(String path){
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory())
        InputStream inputStream
        if(path){
            inputStream = new URL(path).openStream()
        }else{
            inputStream = new URL(HADOOP_PATH).openStream()
        }
        return inputStream
    }
    public void closeConnection(InputStream inputStream){
        IOUtils.closeStream(inputStream)
    }
    // could use hadoop IOUtils to upload, download data,inputstream 的缺陷在于只能用于读取文件流，无法应对文件系统的指令操作
    public void doSomething(InputStream inputStream){
        IOUtils.copyBytes(inputStream,println(),4096,false)
    }

}
