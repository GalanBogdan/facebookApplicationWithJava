package facebookapp.service;

import facebookapp.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {


    List<Post> getPostsFromFollowedUsers();

    void deleteById(Integer id);

    List<Post> getMyPosts();

    void save(Post post);

    void copyPost(Integer id);

    List<Post> getPostsWithMention();

    void likePost(Integer id);

    void unlikePost(Integer id);
}
