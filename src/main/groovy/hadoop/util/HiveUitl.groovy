package hadoop.util

import org.apache.log4j.Logger

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class HiveUitl {
    private static String driver_name = "org.apache.hadoop.hive.jdbc.HiveDriver"
    private static Logger hive_logger = Logger.getLogger(HiveUitl.class)

    public boolean create_Hive_DB(String db_name){
        // Register driver and create driver instance
        Class.forName(driver_name);
        // get connection
        Connection con = DriverManager.getConnection("jdbc:hive://localhost:10000/default", "", "");
        Statement stmt = con.createStatement();
        stmt.executeQuery("CREATE DATABASE userdb");
        System.out.println("Database userdb created successfully.");

        con.close();
    }
}
