package com.example.VideoClubApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
        @Bean
        public static PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())  // Disabling CSRF
                    .authorizeHttpRequests((authorize) -> {
                        authorize.anyRequest().authenticated();
                    }).httpBasic(Customizer.withDefaults());
            return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            UserDetails user1 = User.builder()
                    .username("nikos")
                    .password(passwordEncoder().encode("12345"))
                    .roles("USER")
                    .build();

            UserDetails user2 = User.builder()
                    .username("restas")
                    .password(passwordEncoder().encode("1234"))
                    .roles("ADMIN")
                    .build();

            return new InMemoryUserDetailsManager(user1, user2);
        }
}
