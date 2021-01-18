package com.beditsch.project.service;

import com.beditsch.project.dto.RestaurantRequest;
import com.beditsch.project.exception.OrderNotFoundException;
import com.beditsch.project.exception.RestaurantNotFoundException;
import com.beditsch.project.model.*;
import com.beditsch.project.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;


    public Restaurant getRestaurantById(Integer restaurantId){
        Optional<Restaurant> restaurant = Optional.ofNullable(restaurantRepository.findById(restaurantId).orElseThrow(RestaurantNotFoundException::new));
        return restaurant.get();
    }

    public boolean restaurantExists(Integer restaurantId) {
        return restaurantRepository.existsById(restaurantId);
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public boolean checkOwnership(Restaurant restaurant) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> staff = restaurant.getStaff();
        for (User temp : staff) {
            if (temp.getUsername().equals(username)) return true;
        }
        return false;
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

}
