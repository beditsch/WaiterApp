package com.beditsch.project.dto;

import com.beditsch.project.model.Restaurant;

public class UserGetResponse {
    private int userId;
    private String username;
    private Restaurant restaurant;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
