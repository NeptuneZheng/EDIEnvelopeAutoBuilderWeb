package service;

import org.junit.Test;

public class BuildSQLTest {
    BuildSQL buildSQL = new BuildSQL();
    @Test
    public void buildSQLFile() throws Exception {
        buildSQL.buildSQLFile("D:\\A\\int_cde.txt","D:\\A\\ext_cde.txt","D:\\A\\result_SQL.txt","D:\\A\\remarks.txt");
    }

}