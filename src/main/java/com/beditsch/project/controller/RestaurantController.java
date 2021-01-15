package com.beditsch.project.controller;


import com.beditsch.project.dto.RestaurantRequest;
import com.beditsch.project.exception.RestaurantCannotBeAsignedToUserException;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.User;
import com.beditsch.project.service.RestaurantService;
import com.beditsch.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;


    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{restaurantId}"
    )
    public Restaurant getRestaurantById(@PathVariable("restaurantId") int restaurantId) {
        return restaurantService.getRestaurantById(restaurantId);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Restaurant createRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) userService.loadUserByUsername(username);
        //one restaurant per user
        if (user.getRestaurant() != null) {
            throw new RestaurantCannotBeAsignedToUserException();
        }

        List<User> staff = new ArrayList<>();
        staff.add(user);

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.getName());
        restaurant.setStaff(staff);

        restaurant = restaurantService.createRestaurant(restaurant);

        user.setRestaurant(restaurant);
        userService.updateUser(user);

        return restaurant;
    }
}
