package com.beditsch.project.service;

import com.beditsch.project.exception.OrderNotFoundException;
import com.beditsch.project.exception.RestaurantNotFoundException;
import com.beditsch.project.model.Order;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

}
