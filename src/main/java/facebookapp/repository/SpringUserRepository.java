package facebookapp.repository;

import facebookapp.entity.SpringUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringUserRepository extends JpaRepository<SpringUser, Integer> {

    // Retrieves a user by their username
    SpringUser findByUsername(String username);

    // Searches for users based on a keyword matching their username, first name, or last name
    @Query(
            value = "SELECT * FROM spring_user u WHERE u.username LIKE %:keyword% OR u.first_name LIKE %:keyword% OR " +
                    "u.last_name LIKE %:keyword% ",
            nativeQuery = true)
    List<SpringUser> searchUser(@Param("keyword") String keyword);

    // Inserts a new entry into the follow table to represent a user following another user
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO follow(follower_id, followed_id) VALUES (:followerId, :followedId)", nativeQuery = true)
    void insertIntoFollowTable(@Param("followerId") Integer followerId, @Param("followedId") Integer followedId);

    // Retrieves a list of users followed by a specific user
    @Query(value = """
            SELECT b.user_id, b.username, b.last_name, b.first_name, b.email FROM spring_user a INNER JOIN follow
            ON a.user_id = follow.follower_id
            INNER JOIN spring_user b
            ON b.user_id = follow.followed_id
            WHERE a.user_id = ?;
               """, nativeQuery = true)
    List<Object[]> getFollowedUsers(Integer id);

    // Deletes a follower from the follow table based on the followed user's ID
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM follow WHERE followed_id = ?", nativeQuery = true)
    void deleteFollower(Integer id);

    // Deletes an entry from the follow table based on the follower's ID and followed user's ID
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM follow WHERE follower_id = :followerId and followed_id = :followedId", nativeQuery = true)
    void deleteFromFollowTable(@Param("followerId") Integer followerId, @Param("followedId") Integer followedId);
}
