package facebookapp.service.impl;

import facebookapp.entity.Role;
import facebookapp.entity.SpringUser;
import facebookapp.repository.PostRepository;
import facebookapp.repository.RoleRepository;
import facebookapp.repository.SpringUserRepository;
import facebookapp.service.SpringUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class SpringUserServiceImpl implements SpringUserService, UserDetailsService {

    @Autowired
    private SpringUserRepository springUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public SpringUser registerUser(SpringUser receivedUser) {
        // Create a new SpringUser based on the received user from the frontend
        SpringUser springUser = new SpringUser(receivedUser);

        // Encrypt the user's password using BCryptPasswordEncoder
        springUser.setPassword(new BCryptPasswordEncoder().encode(receivedUser.getPassword()));

        // Set the posts of the user
        springUser.setPosts(receivedUser.getPosts());

        // Update the roles of the user
        springUser.getRoles().forEach(role -> {
            final Role roleByName = roleRepository.findByName(role.getName());
            role.setId(roleByName.getId());
        });

        // Save the user to the repository
        return springUserRepository.save(springUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load the user by their username
        SpringUser springUser = springUserRepository.findByUsername(username);

        // Get the authorities (roles) of the user
        List<GrantedAuthority> authorities = getUserAuthority(springUser.getRoles());

        // Create and return a UserDetails object for the authenticated user
        return new SpringUser(
                springUser.getUsername(),
                springUser.getPassword(),
                springUser.isEnabled(),
                springUser.isAccountNonExpired(),
                springUser.isAccountNonLocked(),
                springUser.isCredentialsNonExpired(),
                authorities);
    }

    @Override
    public List<SpringUser> getAllUsers() {
        // Retrieve all users from the repository
        return springUserRepository.findAll();
    }

    @Override
    public List<SpringUser> searchUser(String keyword) {
        // If the keyword is null, set it to an empty string
        if (keyword == null)
            keyword = "";

        // Search for users based on the keyword
        return springUserRepository.searchUser(keyword);
    }

    @Override
    public void followUser(Integer id) {
        // Find the user to be followed
        Optional<SpringUser> toBeFollowed = springUserRepository.findById(id);

        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());

        // Follow the user by inserting an entry in the "follow" table
        toBeFollowed.ifPresent(user -> springUserRepository.insertIntoFollowTable(loggedInUser.getId(), user.getId()));
    }

    @Override
    public List<SpringUser> getFollowedUsers() {
        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());

        // Get the list of followed users for the logged-in user
        return springUserRepository.getFollowedUsers(loggedInUser.getId())
                .stream()
                .map(elem -> new SpringUser(elem[0].toString(), elem[1].toString(), elem[2].toString(), elem[3].toString(), elem[4].toString()))
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        // Find the user by ID
        Optional<SpringUser> springUser = springUserRepository.findById(id);

        // Delete the user and related data if found
        springUser.ifPresent(user -> {
            springUserRepository.deleteFollower(id);
            postRepository.deleteByUserId(user.getId());
            springUserRepository.deleteById(id);
        });
    }

    @Override
    public void unfollowUser(Integer id) {
        // Find the user to be unfollowed
        Optional<SpringUser> toBeUnfollowed = springUserRepository.findById(id);

        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());

        // Unfollow the user by deleting the entry from the "follow" table
        toBeUnfollowed.ifPresent(user -> springUserRepository.deleteFromFollowTable(loggedInUser.getId(), user.getId()));
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        // Convert the user's roles to a list of GrantedAuthority objects
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }
}

