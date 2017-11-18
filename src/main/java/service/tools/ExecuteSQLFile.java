package service.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;

import jdk.nashorn.api.scripting.ScriptUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.resource.EncodedResource;

/**
 * Created by ZHENGNE on 11/18/2017.
 */
public class ExecuteSQLFile {
//    private static Logger LOG =Logger.getLogger(ExecuteSQLFile.class.getName());



    public boolean executeRunnerAbleFile(String filePath) {
        boolean status = true;
        Connection conn = null;
        try {
            conn = getB2BEDIQA1_DEVPLSQL_DBConn();
        } catch (Exception e) {
//            LOG.error("DB connection error: ",e);
            e.printStackTrace();
        }
        File files = new File(filePath);
        if(files.exists()){
            for(File file:files.listFiles()){
                if(file.isDirectory()){
                    executeRunnerAbleFile(file.getPath());
                }else if(file.isFile()){
                    try {
                        execSqlFileByMysql(file,conn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        status = false;
                    }
                }
            }
            if(conn !=null){
                close(conn);
            }
        }

        return status;
    }

    private static void execSqlFileByMysql(File sqlFile,Connection conn) throws Exception{


        Exception error = null;
        try {
            ScriptRunner runner = new ScriptRunner(conn);
            //下面配置不要随意更改，否则会出现各种问题
            Resources.setCharset(Charset.forName("GBK")); //设置字符集,不然中文乱码插入错误
            runner.setAutoCommit(true);//自动提交
            runner.setFullLineDelimiter(false);
            runner.setDelimiter(";");////每条命令间的分隔符
            runner.setSendFullScript(true);
            runner.setStopOnError(false);
//            runner.setLogWriter(null);//设置是否输出日志
            //如果又多个sql文件，可以写多个runner.runScript(xxx),
            runner.runScript(new InputStreamReader(new FileInputStream(sqlFile),"utf-8"));
        } catch (Exception e) {
//            LOG.error("执行sql文件进行数据库创建失败....",e);
            error = e;
        }
        if(error != null){
            throw error;
        }
    }

    private static void close(Connection conn){
        try {
            if(conn != null){
                conn.close();
            }
        } catch (Exception e) {
            if(conn != null){
                conn = null;
            }
        }
    }

    private static void deleteFile(File file){
        boolean status = false;
        if(file.exists()){
            status = file.delete();
        }
    }

    private Connection getConnection(String dbip, String port, String sid, String user, String psw, String envname) throws Exception {
        Connection conn=null;
        String url = "jdbc:oracle:thin:@"+dbip+":"+port+":"+sid;
        String className="oracle.jdbc.driver.OracleDriver";
        Class.forName(className);
        conn=DriverManager.getConnection(url, user, psw);

        System.out.println(envname+" DB connection init.");

        return conn;
    }

    public Connection getB2BEDIQA1_DEVPLSQL_DBConn() throws Exception {
        String envname = "ediqa1";
        String dbip = "b2bdbqa3";
        String port = "1521";
        String sid = "b2bqa3";
        String user = "b2b_app";
        String psw = "b2bapp";

        return getConnection(dbip, port, sid, user, psw, envname);
    }

}
