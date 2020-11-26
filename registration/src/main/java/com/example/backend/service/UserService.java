package com.example.backend.service;

import com.example.backend.payload.UserRequest;
import com.example.backend.payload.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    String saveUser(UserRequest request);
    List<UserResponse> findAll();

}