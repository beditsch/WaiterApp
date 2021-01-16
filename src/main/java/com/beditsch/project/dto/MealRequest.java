package com.beditsch.project.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MealRequest {
    @NotNull(message = "Please provide a valid restaurant ID.")
    private Integer restaurantId;

    @NotNull(message = "Please provide a valid food category ID")
    private Integer foodCategoryId;

    @NotNull(message = "Please provide a valid meal name.")
    private String name;

    @NotNull(message = "Please provide a valid price.")
    private Double price;

    private Boolean available;

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(Integer foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
