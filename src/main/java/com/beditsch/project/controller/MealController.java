package com.beditsch.project.controller;

import com.beditsch.project.dto.MealRequest;
import com.beditsch.project.dto.MealUpdateRequest;
import com.beditsch.project.exception.AccessDeniedException;
import com.beditsch.project.model.FoodCategory;
import com.beditsch.project.model.Meal;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.User;
import com.beditsch.project.repository.MealRepository;
import com.beditsch.project.service.FoodCategoryService;
import com.beditsch.project.service.MealService;
import com.beditsch.project.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("meal")
public class MealController {
    @Autowired
    private MealService mealService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private FoodCategoryService foodCategoryService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Meal createMeal (@Valid @RequestBody MealRequest mealRequest) {

        Restaurant restaurant = restaurantService.getRestaurantById(mealRequest.getRestaurantId());

        if (!restaurantService.checkOwnership(restaurant))
            throw new AccessDeniedException();

        FoodCategory foodCategory = foodCategoryService.getFoodCategoryById(mealRequest.getFoodCategoryId());
        if (foodCategory.getRestaurant().getId() != restaurant.getId())
            throw new AccessDeniedException();

        Meal meal = new Meal();
        meal.setRestaurant(restaurant);
        meal.setFoodCategory(foodCategory);
        meal.setName(mealRequest.getName());
        meal.setPrice(mealRequest.getPrice());
        boolean available = mealRequest.getAvailable() != null && mealRequest.getAvailable();
        meal.setAvailable(available);

        return mealService.createMeal(meal);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "{mealId}"
    )
    public Meal updateMealById(@RequestBody MealUpdateRequest mealUpdateRequest, @PathVariable @NotNull Integer mealId) {
        Meal meal = mealService.getMealById(mealId);

        if(!restaurantService.checkOwnership(meal.getRestaurant()))
            throw new AccessDeniedException();

        Integer foodCategoryId = mealUpdateRequest.getFoodCategoryId();
        if(foodCategoryId != null) {
            FoodCategory foodCategory = foodCategoryService.getFoodCategoryById(foodCategoryId);
            meal.setFoodCategory(foodCategory);
        }

        String name = mealUpdateRequest.getName();
        if (name != null) meal.setName(name);

        Double price = mealUpdateRequest.getPrice();
        if (price != null) meal.setPrice(price);

        Boolean available = mealUpdateRequest.getAvailable();
        if(available != null) meal.setAvailable(available);

        return mealService.updateMeal(meal);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "{mealId}"
    )
    public void removeMealById(@PathVariable("mealId") @NotNull Integer mealId) {
        mealService.deleteMealById(mealId);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "{mealId}"
    )
    public Meal getMealById(@PathVariable @NotNull Integer mealId) {
        Meal meal = mealService.getMealById(mealId);

        if(!restaurantService.checkOwnership(meal.getRestaurant()))
            throw new AccessDeniedException();

        return meal;
    }
}
