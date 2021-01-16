package com.beditsch.project.controller;

import com.beditsch.project.dto.MealRequest;
import com.beditsch.project.exception.AccessDeniedException;
import com.beditsch.project.model.FoodCategory;
import com.beditsch.project.model.Meal;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.User;
import com.beditsch.project.service.FoodCategoryService;
import com.beditsch.project.service.MealService;
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

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> staff = restaurant.getStaff();
        boolean userHasPermissions = false;
        for (User temp : staff) {
            if (temp.getUsername().equals(username)) {
                userHasPermissions = true;
                break;
            }
        }
        if (!userHasPermissions)
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
}
