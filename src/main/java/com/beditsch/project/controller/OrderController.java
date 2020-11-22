package com.beditsch.project.controller;

import com.beditsch.project.dto.OrderRequest;
import com.beditsch.project.exception.*;
import com.beditsch.project.model.Meal;
import com.beditsch.project.model.Order;
import com.beditsch.project.model.Table;
import com.beditsch.project.service.MealService;
import com.beditsch.project.service.OrderService;
import com.beditsch.project.service.RestaurantService;
import com.beditsch.project.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private MealService mealService;
    @Autowired
    private TableService tableService;




    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "all"
    )
    public List<Order> findAllOrders() {
        return orderService.getAllOrders();
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "{restaurantId}"
    )
    public Order createNewOrder(@RequestBody Order newOrder, @PathVariable int restaurantId) {
        if(newOrder.getTable() == null)
            throw new BadOrderRequestException();

        //If  restaurant does not exist
        if(!restaurantService.restaurantExists(restaurantId))
            throw new RestaurantNotFoundException();

        //If table does not belong to this restaurant
        Table table = tableService.getTableById(newOrder.getTable().getId());
        if(table.getRestaurant().getId() != restaurantId)
            throw new TableNotFoundException();
        newOrder.setTable(table);

        //If any of the meals does not belong to this restaurant
        Meal temporary;
        List<Meal> fullMealList = new ArrayList<>();
        for(Meal temp : newOrder.getMealList()) {
            temporary = mealService.getMealById(temp.getId());

            if ((temporary.getRestaurant().getId() != restaurantId) || !temporary.isAvailable())
                throw new MealNotFoundException();

            fullMealList.add(temporary);
        }
        newOrder.setMealList(fullMealList);

        newOrder.setRestaurant(restaurantService.getRestaurantById(restaurantId));
        return orderService.createNewOrder(newOrder);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{orderId}"
    )
    public Order findOrderById(@PathVariable("orderId") int orderId) {
        return orderService.getOrderById(orderId);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "delete/{orderId}"
    )
    public void removeOrderById(@PathVariable("orderId") int orderId) {
        orderService.deleteOrderById(orderId);
    }

}
