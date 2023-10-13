package com.example.projectcourse4.controller;

import com.example.projectcourse4.entity.Supplier;
import com.example.projectcourse4.entity.User;
import com.example.projectcourse4.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<User> getAll () {
        return userService.getAll();
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<Optional<User>> findByUsername (@PathVariable String username) {
        return new ResponseEntity<Optional<User>>(userService.findByUsername(username),HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(Long userId) {
        userService.deleteById(userId);
    }

    @PostMapping("/saveUser")
    @ResponseBody
    public User addUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/updateUser")
    public User updateUsers(@RequestBody User user){
        return userService.update(user);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}

