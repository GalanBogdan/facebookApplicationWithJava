package facebookapp.startup;

import facebookapp.entity.Post;
import facebookapp.entity.Role;
import facebookapp.entity.SpringUser;
import facebookapp.repository.PostRepository;
import facebookapp.repository.RoleRepository;
import facebookapp.service.SpringUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RunAtStartup {


    private final RoleRepository roleRepository;

    private final SpringUserService springUserService;

    private final PostRepository postRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void insertRestaurantsIntoDB() {


        Role role = new Role("ROLE_USER");
        Role savedRole = roleRepository.save(role);

        role = new Role("ROLE_ADMIN");
        roleRepository.save(role);

        SpringUser springUser = new SpringUser();
        springUser.setPassword("pass");
        springUser.setEmail("user@email.com");
        springUser.setUsername("user");
        springUser.setFirstName("First name");
        springUser.setLastName("Last name");
        springUser.setRoles(Collections.singleton(savedRole));
        springUser.setEnabled(true);
        springUser.setAccountNonExpired(true);
        springUser.setAccountNonLocked(true);
        springUser.setCredentialsNonExpired(true);
        Set<Post> posts = Set.of(new Post(" user message post 1", LocalDateTime.now(), springUser),
                new Post("user message post 2", LocalDateTime.now(), springUser),
                new Post("user message post 3", LocalDateTime.now(), springUser));
        springUser.setPosts(posts);
        postRepository.saveAll(posts);
        springUserService.registerUser(springUser);

        SpringUser springUser2 = new SpringUser();
        springUser2.setPassword("pass");
        springUser2.setEmail("gbogdan@email.com");
        springUser2.setUsername("gbogdan");
        springUser2.setFirstName("Bogdan");
        springUser2.setLastName("Galan");
        springUser2.setRoles(Collections.singleton(savedRole));
        springUser2.setEnabled(true);
        springUser2.setAccountNonExpired(true);
        springUser2.setAccountNonLocked(true);
        springUser2.setCredentialsNonExpired(true);
        Set<Post> posts2 = Set.of(new Post(" gbogdan message post 1 user", LocalDateTime.now(), springUser2),
                new Post("gbogdan message post 2", LocalDateTime.now(), springUser2),
                new Post("gbogdan message post 3", LocalDateTime.now(), springUser2));
        springUser2.setPosts(posts2);
        postRepository.saveAll(posts2);
        springUserService.registerUser(springUser2);

        SpringUser springUser3 = new SpringUser();
        springUser3.setPassword("pass");
        springUser3.setEmail("pgirdea@email.com");
        springUser3.setUsername("pgirdea");
        springUser3.setFirstName("Paula");
        springUser3.setLastName("Girdea");
        springUser3.setRoles(Collections.singleton(savedRole));
        springUser3.setEnabled(true);
        springUser3.setAccountNonExpired(true);
        springUser3.setAccountNonLocked(true);
        springUser3.setCredentialsNonExpired(true);
        Set<Post> posts3 = Set.of(new Post(" pgirdea message post 1", LocalDateTime.now(), springUser3),
                new Post("pgirdea message post 2", LocalDateTime.now(), springUser3),
                new Post("pgirdea message post 3", LocalDateTime.now(), springUser3));
        springUser3.setPosts(posts3);
        postRepository.saveAll(posts3);
        springUserService.registerUser(springUser3);

        SpringUser springUser4 = new SpringUser();
        springUser4.setPassword("7P@ssw0rd!");
        springUser4.setEmail("bthompson92@example.com");
        springUser4.setUsername("bthompson92");
        springUser4.setFirstName("Benjamin ");
        springUser4.setLastName("Thompson");
        springUser4.setRoles(Collections.singleton(savedRole));
        springUser4.setEnabled(true);
        springUser4.setAccountNonExpired(true);
        springUser4.setAccountNonLocked(true);
        springUser4.setCredentialsNonExpired(true);
        Set<Post> posts4 = Set.of(new Post(" bthompson92 message post 5", LocalDateTime.now(), springUser4),
                new Post("bthompson92 message post 6", LocalDateTime.now(), springUser4),
                new Post("bthompson92 message post 7", LocalDateTime.now(), springUser4));
        springUser4.setPosts(posts4);
        postRepository.saveAll(posts4);
        springUserService.registerUser(springUser4);

        SpringUser springUser5 = new SpringUser();
        springUser5.setPassword("Str0ngP@ss!");
        springUser5.setEmail("s.rodriguez34@example.com");
        springUser5.setUsername("srodriguez34");
        springUser5.setFirstName("Sophia");
        springUser5.setLastName("Rodriguez");
        springUser5.setRoles(Collections.singleton(savedRole));
        springUser5.setEnabled(true);
        springUser5.setAccountNonExpired(true);
        springUser5.setAccountNonLocked(true);
        springUser5.setCredentialsNonExpired(true);
        Set<Post> posts5 = Set.of(new Post(" srodriguez34 message post 6", LocalDateTime.now(), springUser5),
                new Post("srodriguez34 message post 7", LocalDateTime.now(), springUser5),
                new Post("srodriguez34 message post 8", LocalDateTime.now(), springUser5));
        springUser5.setPosts(posts5);
        postRepository.saveAll(posts5);
        springUserService.registerUser(springUser5);

        SpringUser springUser6 = new SpringUser();
        springUser6.setPassword("Walker#2023@ss!");
        springUser6.setEmail("ethan.walker17@example.com");
        springUser6.setUsername("ewalker17");
        springUser6.setFirstName("Ethan");
        springUser6.setLastName("Walker");
        springUser6.setRoles(Collections.singleton(savedRole));
        springUser6.setEnabled(true);
        springUser6.setAccountNonExpired(true);
        springUser6.setAccountNonLocked(true);
        springUser6.setCredentialsNonExpired(true);
        Set<Post> posts6 = Set.of(new Post(" ewalker17 message post 7", LocalDateTime.now(), springUser6),
                new Post("ewalker17 message post 8", LocalDateTime.now(), springUser6),
                new Post("ewalker17 message post 9", LocalDateTime.now(), springUser6));
        springUser6.setPosts(posts6);
        postRepository.saveAll(posts6);
        springUserService.registerUser(springUser6);

    }
}
