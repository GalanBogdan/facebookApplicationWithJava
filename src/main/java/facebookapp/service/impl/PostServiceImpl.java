package facebookapp.service.impl;

import facebookapp.entity.Post;
import facebookapp.entity.SpringUser;
import facebookapp.repository.PostRepository;
import facebookapp.repository.SpringUserRepository;
import facebookapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final SpringUserRepository springUserRepository;

    @Override
    public List<Post> getPostsFromFollowedUsers() {
        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());

        // Get the users followed by the logged-in user
        List<SpringUser> springUsers = springUserRepository.getFollowedUsers(loggedInUser.getId())
                .stream()
                .map(elem -> new SpringUser(
                        elem[0].toString(),
                        elem[1].toString(),
                        elem[2].toString(),
                        elem[3].toString(),
                        elem[4].toString()))
                .toList();

        // Retrieve posts from the followed users
        return springUsers.stream()
                .map(user -> postRepository.findByUserId(user.getId()))
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        // Find the post by ID
        Optional<Post> post = postRepository.findById(id);
        post.ifPresent(p -> {
            // Remove the association with the user
            p.setSpringUser(null);
            postRepository.save(p);
            // Delete the post and its likes
            postRepository.deleteByPostId(id);
            postRepository.deleteById(id);
        });
    }

    @Override
    public List<Post> getMyPosts() {
        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());

        // Retrieve posts of the logged-in user
        return postRepository.findByUserId(loggedInUser.getId());
    }

    @Override
    public void save(Post post) {
        // Set the current timestamp for the post
        post.setTimestamp(LocalDateTime.now());

        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());

        // Associate the post with the logged-in user and save it
        post.setSpringUser(loggedInUser);
        postRepository.save(post);
    }

    @Override
    public void copyPost(Integer id) {
        // Find the post to copy
        Optional<Post> optionalPost = postRepository.findById(id);
        optionalPost.ifPresent(p -> {
            // Create a new post with the same message and timestamp
            Post post = new Post();
            post.setTimestamp(p.getTimestamp());
            post.setMessage(p.getMessage());
            // Save the new post
            save(post);
        });
    }

    @Override
    public List<Post> getPostsWithMention() {
        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Retrieve posts containing a mention of the logged-in user
        return postRepository.getPostsWithMention(authentication.getName());
    }

    @Override
    public void likePost(Integer postId) {
        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());

        // Like the specified post by adding an entry in the "likes" table
        postRepository.likePost(loggedInUser.getId(), postId);
    }

    @Override
    public void unlikePost(Integer postId) {
        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());

        // Unlike the specified post by removing the entry from the "likes" table
        postRepository.unlikePost(loggedInUser.getId(), postId);
    }
}

