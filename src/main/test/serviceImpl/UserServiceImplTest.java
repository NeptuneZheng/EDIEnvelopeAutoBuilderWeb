package serviceImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.UserService;

public class UserServiceImplTest {
    private  static UserService userService;

    @Before
    public void setUp() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("springmvc-servlet.xml");
        userService = (UserService) context.getBean("UserServiceImpl");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findOneUserByID() throws Exception {
        Assert.assertEquals(userService.findOneUserByID("222").getUserName(),"Tracy");
    }

    @Test
    public void addOrUpdateUser() throws Exception {
    }

    @Test
    public void removeUserByID() throws Exception {
    }

    @Test
    public void findAllUser() throws Exception {
    }

}