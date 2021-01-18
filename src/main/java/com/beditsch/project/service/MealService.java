package com.beditsch.project.service;

import com.beditsch.project.exception.*;
import com.beditsch.project.model.Meal;
import com.beditsch.project.model.Order;
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
        Optional<Meal> meal = Optional.ofNullable(mealRepository.findById(mealId).orElseThrow(MealNotFoundException::new));
        return meal.get();
    }

    public Meal createMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public Meal updateMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public void deleteMealById(Integer mealId){
        Optional<Meal> meal = mealRepository.findById(mealId);
        if (meal.isEmpty()) {
            throw new MealNotFoundException();
        }
        else if (!meal.get().getOrderList().isEmpty()) {
            throw new MealCannotBeDeletedException();
        }
        mealRepository.deleteById(mealId);
    }
}
