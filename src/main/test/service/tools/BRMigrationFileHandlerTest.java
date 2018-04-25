package service.tools;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BRMigrationFileHandlerTest {
    private  static BRMigrationFileHandler brHan;

    @Before
    public void setUp() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("springmvc-servlet.xml");
        brHan = (BRMigrationFileHandler) context.getBean("BRMigrationFileHandler");
    }
    @Test
    public void distributeFileAndRecord() throws Exception {
        brHan.distributeFileAndRecord("D:\\Data\\Test\\I","D:\\Data\\Test\\O","COSU","20180416");
    }

    @Test
    public void getSelectedFileNameListTest() throws Exception {
//        brHan.GET_NONE_DUPLICATE_AND_MATCHED_LIST("20180416");
//        brHan.getSelectedFileNameList(brHan.GET_NONE_DUPLICATE_AND_MATCHED_LIST("20180416"));
//        brHan.selectFile("D:\\Data\\Test\\O\\O_EDI",brHan.getSelectedFileNameList(brHan.GET_NONE_DUPLICATE_AND_MATCHED_LIST("20180416")));
        brHan.selectDataSet("D:\\Data\\Test\\O",brHan.getSelectedFileNameList(brHan.GET_NONE_DUPLICATE_AND_MATCHED_LIST("20180416")));
    }

}