package service;

import org.junit.Test;

import java.io.File;
import java.util.Date;

public class BuildSQLTest {
    BuildSQL buildSQL = new BuildSQL();
    @Test
    public void buildSQLFile() throws Exception {
        buildSQL.buildSQLFile("D:\\A\\int_cde.txt","D:\\A\\ext_cde.txt","D:\\A\\result_SQL.txt","D:\\A\\remarks.txt");
    }

    @Test
    public void localTest(){
        String dataPath = "D:\\Data\\Test";
        File out_folder = new File(dataPath + "/" + new Date().getMinutes() + "min_Select");
        out_folder.mkdir();
    }
}