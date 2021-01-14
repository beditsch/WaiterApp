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
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Order createNewOrder(@RequestBody OrderRequest orderRequest) {
        if(orderRequest.getTableId() == null)
            throw new BadOrderRequestException();

        //If  restaurant does not exist
        if(!restaurantService.restaurantExists(orderRequest.getRestaurantId()))
            throw new RestaurantNotFoundException();

        //If table does not belong to this restaurant
        Table table = tableService.getTableById(orderRequest.getTableId());
        if(table.getRestaurant().getId() != orderRequest.getRestaurantId())
            throw new TableNotFoundException();

        Order order = new Order();
        order.setTable(table);

        //If any of the meals does not belong to this restaurant
        Meal temporary;
        List<Meal> fullMealList = new ArrayList<>();
        for(Integer temp : orderRequest.getMealsIdList()) {
            temporary = mealService.getMealById(temp);

            if ((temporary.getRestaurant().getId() != orderRequest.getRestaurantId()) || !temporary.isAvailable())
                throw new MealNotFoundException();

            fullMealList.add(temporary);
        }
        order.setMealList(fullMealList);
        order.setRestaurant(restaurantService.getRestaurantById(orderRequest.getRestaurantId()));
        return orderService.createOrder(order);
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
