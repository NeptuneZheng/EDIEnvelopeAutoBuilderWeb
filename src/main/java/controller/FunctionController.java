package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.UserService;

@Controller
public class FunctionController {

    private static UserService userService;
    @Autowired
    public FunctionController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/autoHeaderGenerate",method = RequestMethod.GET)
    public String returnHello1Page(){
        return "autoHeaderGenerate";
    }
}
