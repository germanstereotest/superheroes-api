package com.gpesce.challenge.superheroapi;

import com.gpesce.challenge.superheroapi.model.User;
import com.gpesce.challenge.superheroapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {

        this.users.save(User.builder()
                .username("user")
                .password(this.passwordEncoder.encode("user"))
                .roles(Arrays.asList("ROLE_USER"))
                .build()
        );
        
        this.users.save(User.builder()
                .username("admin")
                .password(this.passwordEncoder.encode("admin"))
                .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                .build()
        );

    }
}
