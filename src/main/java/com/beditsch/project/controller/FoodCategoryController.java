package com.beditsch.project.controller;

import com.beditsch.project.dto.FoodCategoryRequest;
import com.beditsch.project.dto.FoodCategoryUpdateRequest;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

        if(!restaurantService.checkOwnership(restaurant))
            throw new AccessDeniedException();

        FoodCategory foodCategory = new FoodCategory();
        foodCategory.setRestaurant(restaurant);
        foodCategory.setName(foodCategoryRequest.getName());
        Integer priority = foodCategoryRequest.getPriority();
        foodCategory.setPriority(priority == null ? 0 : priority);
        return foodCategoryService.createFoodCategory(foodCategory);

    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "{foodCategoryId}"
    )
    public FoodCategory updateFoodCategoryById(@RequestBody FoodCategoryUpdateRequest foodCategoryUpdateRequest, @PathVariable @NotNull Integer foodCategoryId) {
        FoodCategory foodCategory = foodCategoryService.getFoodCategoryById(foodCategoryId);
        Restaurant restaurant = foodCategory.getRestaurant();

        if(!restaurantService.checkOwnership(restaurant))
            throw new AccessDeniedException();

        String name = foodCategoryUpdateRequest.getName();
        if (name != null) foodCategory.setName(name);

        Integer priority = foodCategoryUpdateRequest.getPriority();
        if (priority != null) foodCategory.setPriority(priority);

        return foodCategoryService.updateFoodCategory(foodCategory);
    }
}
