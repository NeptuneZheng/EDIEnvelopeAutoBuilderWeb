package excel;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ExcelAlgorithmUtilsTest {
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

    @Test
    public void test() throws ParseException {
        String strDate = "2018-12-30T19:00:00";
        String fromTimeZone = "GMT";
        String toTimeZone = "GMT+8";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(fromTimeZone));
        Date date = format.parse(strDate);
        format.setTimeZone(TimeZone.getTimeZone(toTimeZone));
        strDate = format.format(date);

        System.out.println(strDate);

//        String str = "XM   \"HOU,CN        ";
//        System.out.println("\"" + str.replaceAll("\"","\"\"") + "\"");

    }

}