package com.beditsch.project.repository;

import com.beditsch.project.model.Meal;
import com.beditsch.project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {

}
