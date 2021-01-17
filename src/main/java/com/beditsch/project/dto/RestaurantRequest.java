package com.beditsch.project.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RestaurantRequest {
    @NotEmpty(message = "Please provide a valid restaurant name.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
