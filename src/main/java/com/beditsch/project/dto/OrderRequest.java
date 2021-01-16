package com.beditsch.project.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {
    @NotNull(message = "Please provide a valid restaurant ID.")
    private Integer restaurantId;
    @NotNull(message = "Please provide a valid table ID.")
    private Integer tableId;
    @NotNull(message = "Please provide a valid meals ID list.")
    private List<Integer> mealsIdList;



    //    -----------------METHODS--------------


    public OrderRequest() {
    }

    public OrderRequest(Integer restaurantId, Integer tableId, List<Integer> mealsIdList) {
        this.restaurantId = restaurantId;
        this.tableId = tableId;
        this.mealsIdList = mealsIdList;
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

    public List<Integer> getMealsIdList() {
        return mealsIdList;
    }

    public void setMealsIdList(List<Integer> mealsIdList) {
        this.mealsIdList = mealsIdList;
    }
}
