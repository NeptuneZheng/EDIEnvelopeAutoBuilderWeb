package dao.daoImpl;

import dao.UserDBDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.User;

//@ContextConfiguration(value = {"classpath:springmvc-servlet.xml"})
//@WebAppConfiguration("src/main/resources")
public class UserDaoImplTest {

    private static UserDBDao userDBDao;

    @Before
    public void setUp() throws Exception {
        System.out.println("***************Test Init Start*****************");
        System.out.println(System.getProperty("user.dir"));

//        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/springmvc-servlet.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("springmvc-servlet.xml");

        userDBDao = (UserDBDao) context.getBean("UserDaoImpl");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findOneUserByID() throws Exception {
        User user = userDBDao.findOneUserByID("111");
        Assert.assertNotNull(user);
    }

    @Test
    public void addOrUpdateUser() throws Exception {
        User user =  new User("333","ser","password");
        userDBDao.addOrUpdateUser(user);
    }

    @Test
    public void removeUserByID() throws Exception {
        Assert.assertTrue(userDBDao.removeUserByID("333"));
    }

    @Test
    public void findAllUser() throws Exception {
        Assert.assertEquals(userDBDao.findAllUser().size(),2);
    }
}