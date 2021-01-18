package com.beditsch.project.dto;

import org.springframework.data.util.Pair;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderPositionRequest {
    @NotNull(message = "Please provide a valid meal ID.")
    private Integer mealId;
    @NotNull(message = "Please provide a valid quantity")
    @Min(value = 1, message = "Quantity must be positive.")
    private Integer quantity;

    public Integer getMealId() {
        return mealId;
    }

    public void setMealId(Integer mealId) {
        this.mealId = mealId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
