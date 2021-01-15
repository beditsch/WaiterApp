package com.beditsch.project.repository;

import com.beditsch.project.model.FoodCategory;
import com.beditsch.project.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Integer> {

}
