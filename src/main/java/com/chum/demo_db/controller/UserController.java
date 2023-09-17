package com.chum.demo_db.controller;

import com.chum.demo_db.domains.dtos.UserDto;
import com.chum.demo_db.domains.responses.success.ResourceResponse;
import com.chum.demo_db.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value="/update_user")
    public ResponseEntity<?> createOrUpdate(@RequestBody UserDto userDto) {
        return new ResourceResponse<>(userService.createOrUpdateUser(userDto));
    }

    @GetMapping(value = "/user_list")
    public ResponseEntity<?> getAllUser() {
        return new ResourceResponse<>(userService.getAllUsers());
    }
}
