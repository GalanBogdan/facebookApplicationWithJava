package facebookapp.config;

import facebookapp.service.impl.SpringUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configure security settings and authorization rules
        http.authenticationProvider(authenticationProvider())
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/login",
                        "/register",
                        "/api/auth/**",
                        "/v3/api-docs/**",
                        "configuration/**",
                        "/swagger*/**",
                        "/webjars/**",
                        "/swagger-ui/**"
                ).permitAll()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .defaultSuccessUrl("/index").and()
                .logout().logoutSuccessUrl("/login").and()
                .sessionManagement()
                .maximumSessions(1);
        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // Configure authentication provider
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Configure user details service
        return new SpringUserServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // Configure password encoder
        return new BCryptPasswordEncoder();
    }

}
