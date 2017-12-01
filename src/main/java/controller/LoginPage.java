package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.User;
import service.UserService;

/**
 * Created by ZHENGNE on 11/11/2017.
 */
@Controller
public class LoginPage {

    private static UserService userService;
    @Autowired
    public LoginPage(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String returnLoginPage(){
        System.out.print("Get Login Page");
        return "login";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String returnregisterPage(){
        System.out.print("Get Login Page");
        return "register";
    }

    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public String returnHello1Page(){
        System.out.print("Get Login Page");
        return "/main";
    }

    @RequestMapping(value = "/loginVerify",method = RequestMethod.GET)
    @ResponseBody
    public String loginVerify(String username,String password){
        String status = "";
        User user = userService.findOneUserByName(username);
        if(user == null){
            status = "register";
        }else if(user.getPassword().equals(password)){
            status = "true";
        }else {
            status = "false";
        }
        return status;
    }

    @RequestMapping(value = "/registerVerify",method = RequestMethod.GET)
    @ResponseBody
    public String registerVerify(String username,String password){
        String status = "";
        User user = userService.findOneUserByName(username);
        if(user == null){
            user = new User();
            user.setUserName(username);
            user.setPassword(password);
            userService.addOrUpdateUser(user);
            status = "true";
        }else {
            status = "false";
        }
        return status;
    }

}
