package com.consti.security.StudentManagement;

import com.consti.security.user.Student;
import com.consti.security.user.StudentDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentMapper {

    public StudentDTO toDTO(Student student) {
        List<String> roles = student.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        return StudentDTO.builder().firstname(student.getFirstname())
                .lastname(student.getLastname())
                .dob(student.getDob())
                .id(student.getId())
                .email(student.getEmail())
                .role(student.getRole())
                .roles(roles)
                .build();
    }

    public Student toStudent(StudentDTO dto) {

        return Student.builder().firstname(dto.getFirstname())
                            .lastname(dto.getLastname())
                            .dob(dto.getDob()).id(dto.getId())
                            .role(dto.getRole()).build();
    }
}
