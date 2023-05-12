package facebookapp.controller;

import facebookapp.entity.Role;
import facebookapp.entity.SpringUser;
import facebookapp.service.SpringUserService;
import facebookapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;

@Controller
public class RegisterController {

    @Autowired
    private SpringUserService userService;

    @GetMapping(value = "/register")
    public String registerForm(Model model) {
        // Create a new SpringUser object and set default values for account properties
        SpringUser user = new SpringUser();
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        // Add the user object to the model
        model.addAttribute("user", user);
        // Return the name of the register view template
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute("user") @RequestBody SpringUser user) {
        // Set the user's role as ROLE_USER and register the user
        user.setRoles(Collections.singleton(new Role(Constants.ROLE_USER)));
        userService.registerUser(user);
        // Redirect to the login page after successful registration
        return Constants.REDIRECT_TO_LOGIN;
    }
}
