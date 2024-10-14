package com.consti.security.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentDTO {

    private  Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate dob;
    private Role role;
    private List<String> roles;
}

