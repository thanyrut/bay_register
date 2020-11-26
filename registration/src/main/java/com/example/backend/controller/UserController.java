package com.example.backend.controller;

import com.example.backend.payload.UserRequest;
import com.example.backend.payload.UserResponse;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public String saveUser(@NotNull @RequestBody UserRequest userRequest) {
        userRequest.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        String response = userService.saveUser(userRequest);
        return response;
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<UserResponse> findAll(@RequestHeader("Authorization") String authorization) {
        List<UserResponse> response = userService.findAll();
        return response;
    }
}


