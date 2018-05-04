package service;

import org.junit.Test;

public class BuildSQLTest {
    BuildSQL buildSQL = new BuildSQL();
    @Test
    public void buildSQLFile() throws Exception {
        buildSQL.buildSQLFile("D:\\A\\int_cde.txt","D:\\A\\ext_cde.txt","D:\\A\\result_SQL.txt","D:\\A\\remarks.txt");
    }

    @Test
    public void localTest(){
//        String dataPath = "D:\\Data\\Test";
//        File out_folder = new File(dataPath + "/" + new Date().getMinutes() + "min_Select");
//        out_folder.mkdir();(1000000000)/
//        System.out.println("Total time of use for data set copy: " + ((10000000)/Math.pow(10,9)) + "s" );
        String str = "ISA*00*          *00*          *01*CARGOSMART     *ZZ*COSU           *180402*1357*U*00401*019662547*0*P*^~GS*RO*ACZONE*COSU*20180402*1357*19662547*X*004010~ST*300*625470001~B1*COSU**20180402*N~Y1***COSU*****MM~Y2*1*S**20TK~N9*ZZ*CC4791285813~N9*CT*GAC18603~N9*BM*SPSH18031981A~N";
        System.out.println(str.replaceAll("(.*B1(.*?\\*){4})|~.*","") );
    }
}