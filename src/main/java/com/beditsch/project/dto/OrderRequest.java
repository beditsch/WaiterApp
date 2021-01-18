package com.beditsch.project.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {

    @NotNull(message = "Please provide a valid restaurant ID.")
    private Integer restaurantId;
    @NotNull(message = "Please provide a valid table ID.")
    private Integer tableId;
    @NotNull(message = "Please provide a valid  orderPositions list.")
    private List<@Valid OrderPositionRequest> orderPositionsList;



    //    -----------------METHODS--------------


    public OrderRequest() {
    }

    public OrderRequest(@NotNull(message = "Please provide a valid restaurant ID.") Integer restaurantId, @NotNull(message = "Please provide a valid table ID.") Integer tableId, @NotNull(message = "Please provide a valid  (meal, quantity) list.") List<OrderPositionRequest> orderPositionsList) {
        this.restaurantId = restaurantId;
        this.tableId = tableId;
        this.orderPositionsList = orderPositionsList;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public List<OrderPositionRequest> getOrderPositionsList() {
        return orderPositionsList;
    }

    public void setOrderPositionsList(List<OrderPositionRequest> orderPositionsList) {
        this.orderPositionsList = orderPositionsList;
    }
}
