package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ZHENGNE on 11/11/2017.
 */
@Controller
public class LoginPage {
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String returnHelloPage(){
        System.out.print("Get it");
        return "hello";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String returnLoginPage(){
        System.out.print("Get Login Page");
        return "login";
    }


}
