package com.beditsch.project.service;

import com.beditsch.project.model.FoodCategory;
import com.beditsch.project.repository.FoodCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodCategoryService {

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    public FoodCategory createFoodCategory (FoodCategory foodCategory) {
        return foodCategoryRepository.save(foodCategory);
    }
}
