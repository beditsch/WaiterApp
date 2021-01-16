package com.beditsch.project.dto;

import javax.validation.constraints.NotNull;

public class FoodCategoryRequest {
    @NotNull(message = "Please provide a valid restaurant ID.")
    private Integer restaurantId;
    private String name;
    private Integer priority;

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
