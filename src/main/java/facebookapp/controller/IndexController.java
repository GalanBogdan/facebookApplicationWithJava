package facebookapp.controller;
import facebookapp.service.SpringUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    @Autowired
    private SpringUserService userService;

    @RequestMapping(value = {"/index"})
    public String index(Model model) {
        // Get the currently authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Add any necessary model attributes for the view
        // model.addAttribute("restaurants", restaurantRepository.findAll());

        // Return the name of the view to render
        return "index";
    }
}
