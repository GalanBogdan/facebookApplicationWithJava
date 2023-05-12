package facebookapp.controller;

import facebookapp.entity.Post;
import facebookapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping(value = "/all")
    public String getAllPosts(Model model) {
        // Get all posts from followed users and add them to the model
        model.addAttribute("posts", postService.getPostsFromFollowedUsers());
        return "posts";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deletePost(@PathVariable Integer id) {
        // Delete the post with the specified ID
        postService.deleteById(id);
        // Redirect to the "/post/all" URL after deleting the post
        return "redirect:/post/all";
    }

    @GetMapping(value = "/own")
    public String getMyPosts(Model model) {
        // Get the posts created by the current user and add them to the model
        model.addAttribute("posts", postService.getMyPosts());
        return "my-posts";
    }

    @GetMapping(value = "/add")
    public String addPost(Model model) {
        // Create a new Post object and add it to the model
        Post post = new Post();
        model.addAttribute("post", post);
        return "add-post";
    }

    @PostMapping(value = "/add")
    public String addPost(@ModelAttribute("post") @RequestBody Post post) {
        // Save the posted data as a new post
        postService.save(post);
        // Redirect to the "/post/own" URL after adding the post
        return "redirect:/post/own";
    }

    @RequestMapping(value = "/copy/{id}")
    public String copyPost(@PathVariable Integer id) {
        // Copy the post with the specified ID
        postService.copyPost(id);
        // Redirect to the "/post/own" URL after copying the post
        return "redirect:/post/own";
    }

    @GetMapping(value = "/mentions")
    public String getPostWithMentions(Model model) {
        // Get posts that have mentions and add them to the model
        model.addAttribute("posts", postService.getPostsWithMention());
        return "post-mentions";
    }

    @RequestMapping(value = "/like/{id}")
    public String likePost(@PathVariable Integer id) {
        // Like the post with the specified ID
        postService.likePost(id);
        // Redirect to the "/post/all" URL after liking the post
        return "redirect:/post/all";
    }

    @RequestMapping(value = "/unlike/{id}")
    public String unlikePost(@PathVariable Integer id) {
        // Unlike the post with the specified ID
        postService.unlikePost(id);
        // Redirect to the "/post/all" URL after unliking the post
        return "redirect:/post/all";
    }
}
