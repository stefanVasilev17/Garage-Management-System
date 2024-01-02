package com.stefan.security.auth;

import com.stefan.security.config.JwtService;
import com.stefan.security.token.Token;
import com.stefan.security.token.JwtTokenRepository;
import com.stefan.security.token.TokenType;
import com.stefan.security.user.Role;
import com.stefan.security.user.User;
import com.stefan.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtTokenRepository jwtTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("The email exists in the database!");
        }

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();


        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var jwtTokenBuild = Token
                .builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .isRevoked(false)
                .isExpired(false)
                .build();

        jwtTokenRepository.save(jwtTokenBuild);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new NoSuchElementException("Not existing user!" ));

        String jwtToken = jwtService.generateToken(user);
        saveUserToken(user,jwtToken);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
