package com.beditsch.project.dto;


import javax.validation.constraints.NotNull;

public class TableRequest {
    @NotNull(message = "Please provide a valid restaurant ID.")
    private Integer restaurantId;

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
