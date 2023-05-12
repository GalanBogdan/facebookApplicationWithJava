package facebookapp.controller;

import facebookapp.service.SpringUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class SpringUserController {

    private final SpringUserService myUserService;

    @GetMapping
    public String getAllUsers(Model model, String keyword) {
        // Add the list of users to the model, based on the provided keyword for searching
        model.addAttribute("users", myUserService.searchUser(keyword));
        // Return the name of the users view template
        return "users";
    }

    @RequestMapping(value = "/follow/{id}")
    public String followUser(@PathVariable Integer id) {
        // Follow the user with the given ID
        myUserService.followUser(id);
        // Redirect to the users view
        return "redirect:/users";
    }

    @RequestMapping(value = "/unfollow/{id}")
    public String unfollowUser(@PathVariable Integer id) {
        // Unfollow the user with the given ID
        myUserService.unfollowUser(id);
        // Redirect to the users view
        return "redirect:/users";
    }

    @GetMapping(value = "/followed")
    public String getFollowedUsers(Model model) {
        // Add the list of followed users to the model
        model.addAttribute("followedUsers", myUserService.getFollowedUsers());
        // Return the name of the followed-users view template
        return "followed-users";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        // Delete the user with the given ID
        myUserService.deleteById(id);
        // Redirect to the users view
        return "redirect:/users";
    }
}
