package com.consti.security.Auth;

import com.consti.security.config.JwtService;
import com.consti.security.user.Role;
import com.consti.security.user.Student;
import com.consti.security.user.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final PasswordEncoder pwEncoder;
    private final StudentRepository repository;

    private final AuthenticationManager authenticationManager;



    public AuthenticationResponse register(RegisterRequest request) {

        var student = Student.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(pwEncoder.encode(request.getPassword()))
                .dob(request.getDob())
                .role(Role.STUDENT)
                .build();


        Optional<Student> optionalStudent = repository.findByEmail(request.getEmail());
        if(optionalStudent.isPresent()){
            return new AuthenticationResponse();
        }


        repository.save(student);
        var jwtToken = jwtService.generateToken(null, student);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .firstname(student.getFirstname())
                .role(student.getRole())
                .email(student.getEmail())
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var student = repository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(null, student);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .firstname(student.getFirstname())
                .role(student.getRole())
                .email(student.getEmail())
                .build();
    }
}
