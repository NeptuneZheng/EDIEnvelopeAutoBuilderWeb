package service.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.BRMigrationFileSystem;

import java.util.Date;

public class BRMigrationFileHandleServiceImplTest {
    private  static BRMigrationFileHandleServiceImpl brImp;

    @Before
    public void setUp() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("springmvc-servlet.xml");
        brImp = (BRMigrationFileHandleServiceImpl) context.getBean("BRMigrationFileHandleServiceImpl");
    }

    @Test
    public void saveNewRecord() throws Exception {
        BRMigrationFileSystem br = new BRMigrationFileSystem("3.txt","2_1.edi","CC1111","aaa","bbb","20180412","UPD",new Date(),new Date(),"F","T");
        br.setId(1);
        brImp.saveNewRecord(br);
    }

    @Test
    public void findOneBRDataRecord() throws Exception {
        brImp.findOneBRDataRecord("CC4993870828");
    }

    @Test
    public void findDuplicateBRDateRecordByBKG() throws Exception {
    }

    @Test
    public void findDuplicateBRDateRecordByDuplicateFlag() throws Exception {
    }

    @Test
    public void findDuplicateBRDateRecordByMatchFlag() throws Exception {
    }

    @Test
    public void findDuplicateBRDateRecordByDuplicateFlagAndMatchFlag() throws Exception {
    }
}