package facebookapp.controller;

import facebookapp.util.Constants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping(value = {"/login", "/"})
    public String login() {
        // Get the current authentication information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is not authenticated or is an anonymous user
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            // Return the name of the login view
            return "login";
        }

        // If the user is already authenticated, redirect to the index page
        return Constants.REDIRECT_TO_INDEX;
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        // Add a model attribute to indicate login error
        model.addAttribute("loginError", true);

        // Return the name of the login view
        return "login.html";
    }
}
