package com.consti.security.config;

import com.consti.security.user.Role;
import com.consti.security.user.Student;
import com.consti.security.user.StudentRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static java.time.Month.NOVEMBER;


@Configuration
@RequiredArgsConstructor

public class ApplicationConfig {

    private final StudentRepository studentRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> studentRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }

    @Bean

    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Student Consti = new Student(
                    1,
                    "Constantin",
                    "Wessels",
                    "constantin.wessels@web.de",
                    passwordEncoder().encode("1234"),
                    LocalDate.of(1998, NOVEMBER, 19),
                    0,
                    Role.PROFESSOR
            );
            studentRepository.save(Consti);
        };
    }

}
