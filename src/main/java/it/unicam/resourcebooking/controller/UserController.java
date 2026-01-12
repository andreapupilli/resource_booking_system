package it.unicam.resourcebooking.controller;

import it.unicam.resourcebooking.dto.CreateUserRequest;
import it.unicam.resourcebooking.model.User;
import it.unicam.resourcebooking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> list() {
        return userService.list();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@Valid @RequestBody CreateUserRequest req) {
        return userService.create(req);
    }
}
