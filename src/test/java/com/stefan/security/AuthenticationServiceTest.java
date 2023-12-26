package com.stefan.security;

import com.stefan.security.auth.AuthenticationRequest;
import com.stefan.security.auth.AuthenticationResponse;
import com.stefan.security.auth.AuthenticationService;
import com.stefan.security.auth.RegisterRequest;
import com.stefan.security.config.JwtService;
import com.stefan.security.user.Role;
import com.stefan.security.user.User;
import com.stefan.security.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "john@example.com", "password");
        User savedUser = User.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepository.save(any())).thenReturn(savedUser);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertEquals(savedUser.getEmail(), response.getToken()); // Assuming the token is the email for simplicity
        verify(userRepository, times(1)).save(any());
        verify(jwtService, times(1)).generateToken(savedUser);
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("john@example.com", "password");
        User user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("generatedToken");
        when(authenticationManager.authenticate(any())).thenReturn(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertEquals("generatedToken", response.getToken());
        verify(userRepository, times(1)).findByEmail("john@example.com");
        verify(jwtService, times(1)).generateToken(user);
        verify(authenticationManager, times(1)).authenticate(any());
    }

}
