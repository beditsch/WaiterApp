package com.beditsch.project.controller;

import com.beditsch.project.dto.FoodCategoryRequest;
import com.beditsch.project.exception.AccessDeniedException;
import com.beditsch.project.exception.FoodCategoryRequestInvalidException;
import com.beditsch.project.exception.RestaurantNotFoundException;
import com.beditsch.project.model.FoodCategory;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.Table;
import com.beditsch.project.model.User;
import com.beditsch.project.service.FoodCategoryService;
import com.beditsch.project.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("foodCategory")
public class FoodCategoryController {
    @Autowired
    private FoodCategoryService foodCategoryService;
    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public FoodCategory createFoodCategory (@Valid @RequestBody FoodCategoryRequest foodCategoryRequest) {

        Restaurant restaurant = restaurantService.getRestaurantById(foodCategoryRequest.getRestaurantId());
        if(restaurant == null)
            throw new RestaurantNotFoundException();

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> staff = restaurant.getStaff();
        for (User temp : staff) {
            if (temp.getUsername().equals(username)) {
                FoodCategory foodCategory = new FoodCategory();
                foodCategory.setRestaurant(restaurant);
                foodCategory.setName(foodCategoryRequest.getName());
                Integer priority = foodCategoryRequest.getPriority();
                foodCategory.setPriority(priority == null ? 0 : priority);
                return foodCategoryService.createFoodCategory(foodCategory);
            }
        }
        throw new AccessDeniedException();
    }
}
