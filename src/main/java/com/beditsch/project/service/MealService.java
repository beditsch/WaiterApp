package com.beditsch.project.service;

import com.beditsch.project.exception.MealNotFoundException;
import com.beditsch.project.exception.RestaurantNotFoundException;
import com.beditsch.project.model.Meal;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.Table;
import com.beditsch.project.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    public Meal getMealById(Integer mealId){
        Optional<Meal> meal = mealRepository.findById(mealId);
        if (meal.isPresent())
            return meal.get();
        else
            throw new MealNotFoundException();
    }

    public Meal createMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public Meal updateMeal(Meal meal) {
        return mealRepository.save(meal);
    }
}
