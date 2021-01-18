package com.beditsch.project.controller;

import com.beditsch.project.dto.UserGetResponse;
import com.beditsch.project.dto.UserSignUpRequest;
import com.beditsch.project.dto.UserSignUpResponse;
import com.beditsch.project.exception.AccessDeniedException;
import com.beditsch.project.model.User;
import com.beditsch.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public UserSignUpResponse createUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {

        UserSignUpResponse userSignUpResponse = userService.createUser(userSignUpRequest);

        return userSignUpResponse;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{userId}"
    )
    public UserGetResponse getUserById(@PathVariable("userId") @NotNull Integer userId) {
        User user = userService.getUserById(userId);
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(user.getUsername())) {
            UserGetResponse userGetResponse = new UserGetResponse();
            userGetResponse.setRestaurant(user.getRestaurant());
            userGetResponse.setUserId(user.getId());
            userGetResponse.setUsername(user.getUsername());
            return userGetResponse;
        }
        else throw new AccessDeniedException();
    }


}
