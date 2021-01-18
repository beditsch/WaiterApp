package com.beditsch.project.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderStatusUpdateRequest {
    @NotNull(message = "Please provide a valid order ID.")
    @Min(value = 0, message = "Status cannot be negative.")
    @Max(value = 2, message = "Status cannot be greater than 2.")
    private Integer orderStatus;

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
