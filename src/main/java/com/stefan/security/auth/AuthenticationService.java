package com.stefan.security.auth;

import com.stefan.security.config.JwtService;
import com.stefan.security.token.JwtRepository;
import com.stefan.security.token.Token;
import com.stefan.security.token.TokenType;
import com.stefan.security.user.Role;
import com.stefan.security.user.User;
import com.stefan.security.user.UserRepository;
import com.stefan.security.validator.PasswordValidator;
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
    private final JwtRepository jwtTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordValidator passwordValidator;

    public AuthenticationResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("The email exists in the database!");
        }

        passwordValidator.isValidPassword(request.getPassword());

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        User savedUser = userRepository.save(user);
        String jwt = jwtService.generateToken(user);

        saveUserToken(savedUser, jwt);

        return AuthenticationResponse
                .builder()
                .token(jwt)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = jwtTokenRepository.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        jwtTokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var jwtBuild = Token
                .builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .isRevoked(false)
                .isExpired(false)
                .build();

        jwtTokenRepository.save(jwtBuild);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new NoSuchElementException("Not existing user!"));

        String jwt = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwt);

        return AuthenticationResponse
                .builder()
                .token(jwt)
                .build();
    }
}
