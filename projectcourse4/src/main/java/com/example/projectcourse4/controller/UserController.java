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
@RequestMapping("/api/v1/users")
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
    public HttpStatus deleteUser(Long userId) {
        userService.deleteById(userId);
        return HttpStatus.OK;
    }

    @PostMapping("/saveUser")
    @ResponseBody
    public HttpStatus addUser(@RequestBody User user) {
         userService.save(user);
        return HttpStatus.CREATED;
    }

    @GetMapping("/updateUser")
    public HttpStatus updateUser(@RequestBody User user){
         userService.update(user);
        return HttpStatus.OK;
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}

