package com.beditsch.project.controller;


import com.beditsch.project.dto.RestaurantRequest;
import com.beditsch.project.exception.AccessDeniedException;
import com.beditsch.project.exception.RestaurantCannotBeAsignedToUserException;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.User;
import com.beditsch.project.service.RestaurantService;
import com.beditsch.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
    public Restaurant createRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) {
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

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "{restaurantId}"
    )
    public Restaurant updateRestaurantById(@RequestBody RestaurantRequest restaurantRequest, @PathVariable @NotNull Integer restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        if(!restaurantService.checkOwnership(restaurant))
            throw new AccessDeniedException();

        String name = restaurantRequest.getName();
        if(name != null) restaurant.setName(name);

        return restaurantService.updateRestaurant(restaurant);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "{restaurantId}"
    )
    public Restaurant getRestaurantById(@PathVariable @NotNull Integer restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        if(!restaurantService.checkOwnership(restaurant))
            throw new AccessDeniedException();

        return restaurant;
    }

}
