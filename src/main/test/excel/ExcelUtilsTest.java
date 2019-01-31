package excel;

import org.junit.Before;
import org.junit.Test;

public class ExcelUtilsTest {
    ExcelUtils utils = null;

    @Before
    public void init(){
//        this.utils = new ExcelUtils();
    }
    @Test
    public void writeExcel() throws Exception {
        utils.writeExcel(null,"test","D:/A/test.xls");
        utils.writeExcel(null,"test","D:/A/test.xlsx");
    }

    @Test
    public void readeExcel() throws Exception {
    }

}