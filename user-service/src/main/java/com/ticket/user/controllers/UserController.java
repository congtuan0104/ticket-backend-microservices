package com.ticket.user.controllers;

import com.ticket.user.entities.User;
import com.ticket.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Environment env;

    @GetMapping
    public User findUser(@RequestParam(value = "email") String email) {
        // find user by email
        Optional<User> user = userService.findUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()).getBody();

    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }

//    @GetMapping
//    public ResponseTemplateVO getUser(
//            @RequestHeader(value = "id") String userId,
//            @RequestHeader(value = "role") String role) {
//        return userService.getUserWithDepartment(userId);
//    }

    @GetMapping(value = "/secure")
    public String getSecure() {
        return "Secure endpoint available";
    }
}
