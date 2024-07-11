package com.consti.security.Auth;

import com.consti.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    String token;
    String firstname;
    String email;
    Role role;
}
