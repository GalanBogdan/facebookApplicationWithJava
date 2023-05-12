package facebookapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

    @RequestMapping(value = {"/logout"})
    public String logout(){
        // Return the name of the login view
        return "login.html";
    }
}
