package com.beditsch.project.dto;

import com.beditsch.project.model.Meal;
import com.beditsch.project.model.Order;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.Table;

import java.util.List;

public class OrderRequest {
    private int restaurantId;
    private int tableId;
    private List<Meal> mealList;



    //    -----------------METHODS--------------


    public OrderRequest() {
    }

    public OrderRequest(int restaurantId, int tableId, List<Meal> mealList) {
        this.restaurantId = restaurantId;
        this.tableId = tableId;
        this.mealList = mealList;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }
}
