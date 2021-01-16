package com.beditsch.project.service;

import com.beditsch.project.dto.RestaurantRequest;
import com.beditsch.project.exception.OrderNotFoundException;
import com.beditsch.project.exception.RestaurantNotFoundException;
import com.beditsch.project.model.Order;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.Table;
import com.beditsch.project.model.User;
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
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent())
            return restaurant.get();
        else
            throw new RestaurantNotFoundException();
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

}
