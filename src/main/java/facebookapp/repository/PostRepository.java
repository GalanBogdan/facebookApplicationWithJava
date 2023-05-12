package facebookapp.repository;

import facebookapp.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    // Retrieves all posts by a specific user
    @Query(value = "SELECT * FROM post WHERE user_id = ?", nativeQuery = true)
    List<Post> findByUserId(Integer userId);

    // Retrieves posts that contain a specific keyword in the message
    @Query(value = "SELECT * FROM post WHERE message LIKE %:keyword%", nativeQuery = true)
    List<Post> getPostsWithMention(@Param("keyword") String keyword);

    // Deletes all posts associated with a specific user
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM post WHERE user_id = ?", nativeQuery = true)
    void deleteByUserId(Integer id);

    // Likes a post by adding a record in the likes table
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO likes(user_id, post_id) VALUES (:userId, :postId)", nativeQuery = true)
    void likePost(@Param("userId") Integer userId, @Param("postId") Integer postId);

    // Removes a like from a post by deleting the corresponding record in the likes table
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM likes WHERE user_id = :userId AND post_id = :postId", nativeQuery = true)
    void unlikePost(@Param("userId") Integer userId, @Param("postId") Integer postId);

    // Deletes all likes associated with a specific post
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM likes WHERE post_id = ?", nativeQuery = true)
    void deleteByPostId(Integer id);
}
