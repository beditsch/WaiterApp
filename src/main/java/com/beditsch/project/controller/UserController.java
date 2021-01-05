package com.beditsch.project.controller;

import com.beditsch.project.dto.UserSignUpRequest;
import com.beditsch.project.dto.UserSignUpResponse;
import com.beditsch.project.model.User;
import com.beditsch.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public UserSignUpResponse createUser(@RequestBody UserSignUpRequest userSignUpRequest) {

        UserSignUpResponse userSignUpResponse = userService.createUser(userSignUpRequest);

        return userSignUpResponse;
    }
}
