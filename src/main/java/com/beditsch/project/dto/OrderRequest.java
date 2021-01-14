package com.beditsch.project.dto;

import java.util.List;

public class OrderRequest {
    private Integer restaurantId;
    private Integer tableId;
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
