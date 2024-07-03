package com.ticket.user.controllers;

import com.ticket.user.entities.User;
import com.ticket.user.services.UserService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public User findUserByEmail(@RequestParam(value = "email") String email) {
        // find user by email
        Optional<User> user = userService.findUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()).getBody();

    }

    @GetMapping(value = "/{id}")
    public User findUserById(@PathVariable(value = "id") ObjectId id){
        // find user by id
        User user = userService.getById(id);
        return ResponseEntity.ok(user).getBody();
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return userService.save(user);
    }


//    @GetMapping
//    public ResponseTemplateVO getUser(
//            @RequestHeader(value = "id") String userId,
//            @RequestHeader(value = "role") String role) {
//        return userService.getUserWithDepartment(userId);
//    }
}
