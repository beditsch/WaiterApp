package com.beditsch.project.service;

import com.beditsch.project.exception.FoodCategoryNotFoundException;
import com.beditsch.project.exception.MealNotFoundException;
import com.beditsch.project.model.FoodCategory;
import com.beditsch.project.model.Meal;
import com.beditsch.project.model.Order;
import com.beditsch.project.repository.FoodCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FoodCategoryService {

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    public FoodCategory createFoodCategory (FoodCategory foodCategory) {
        return foodCategoryRepository.save(foodCategory);
    }

    public FoodCategory getFoodCategoryById(Integer foodCategoryId){
        Optional<FoodCategory> foodCategory = foodCategoryRepository.findById(foodCategoryId);
        if (foodCategory.isPresent())
            return foodCategory.get();
        else
            throw new FoodCategoryNotFoundException();
    }

    public FoodCategory updateFoodCategory(FoodCategory foodCategory) {
        return foodCategoryRepository.save(foodCategory);
    }
}
