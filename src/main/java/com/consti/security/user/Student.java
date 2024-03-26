package com.consti.security.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "_Student")
@AllArgsConstructor
@NoArgsConstructor

public class Student implements UserDetails {

    @Id
    @GeneratedValue
    private  Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @Transient
    private Integer age;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private Role role;



    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
