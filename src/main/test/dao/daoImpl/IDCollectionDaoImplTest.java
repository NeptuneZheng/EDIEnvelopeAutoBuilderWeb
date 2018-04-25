package dao.daoImpl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.IDCollection;

public class IDCollectionDaoImplTest {

    private  static IDCollectionDaoImpl idImp;

    @Before
    public void setUp() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("springmvc-servlet.xml");
        idImp = (IDCollectionDaoImpl) context.getBean("IDCollectionDaoImpl");
    }
    @Test
    public void getLastMaxId() throws Exception {
        idImp.getLastMaxId("BRMig");
    }

    @Test
    public void updateMaxId() throws Exception {
        idImp.updateMaxId(new IDCollection("BRMig",1));
    }

    @Test
    public void addNewType() throws Exception {
        idImp.addNewType(new IDCollection("BRMig",0));
    }

}