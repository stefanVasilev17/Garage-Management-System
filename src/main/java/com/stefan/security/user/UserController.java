package com.stefan.security.user;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping("/api/get-all-users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
