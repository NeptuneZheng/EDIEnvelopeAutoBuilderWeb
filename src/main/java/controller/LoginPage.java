package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


}
