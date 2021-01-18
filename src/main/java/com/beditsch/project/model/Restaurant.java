package com.beditsch.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "restaurants")
@javax.persistence.Table(name = "restaurants")
public class Restaurant {
    @Id
    @Column(name = "restaurant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(targetEntity = User.class, cascade = CascadeType.ALL, mappedBy = "restaurant")
    @JsonIgnore
    private List<User> staff;

    @OneToMany(targetEntity = Meal.class, cascade=CascadeType.ALL, mappedBy = "restaurant")
    private List<Meal> menu;


    @OneToMany(targetEntity = Order.class, cascade=CascadeType.ALL, mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<Order> orderList;

    @OneToMany(targetEntity = Table.class, cascade=CascadeType.ALL, mappedBy = "restaurant")
    private List<Table> tableList;

    @OneToMany(targetEntity = FoodCategory.class, cascade=CascadeType.ALL, mappedBy = "restaurant")
    private List<FoodCategory> foodCategoryList;





    //    -----------------METHODS--------------

    public Restaurant() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getStaff() {
        return staff;
    }

    public void setStaff(List<User> staff) {
        this.staff = staff;
    }

    public List<Meal> getMenu() {
        return menu;
    }

    public void setMenu(List<Meal> menu) {
        this.menu = menu;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public void setTableList(List<Table> tableList) {
        this.tableList = tableList;
    }

    public List<FoodCategory> getFoodCategoryList() {
        return foodCategoryList;
    }

    public void setFoodCategoryList(List<FoodCategory> foodCategoryList) {
        this.foodCategoryList = foodCategoryList;
    }
}
