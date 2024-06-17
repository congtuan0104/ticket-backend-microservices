package com.ticket.user.controllers;

import com.ticket.user.entities.User;
import com.ticket.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Environment env;

    @GetMapping
    public String home() {
        return "User Service running at port " + env.getProperty("local.server.port");
    }

    @PostMapping
    public User save(@RequestBody User user) {
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
