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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    public Order createNewOrder(@Valid @RequestBody OrderRequest orderRequest) {

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
            path = "{publicOrderId}"
    )
    public Order findOrderByPublicId(@PathVariable("publicOrderId") @NotNull String publicOrderId) {
        return orderService.getOrderByPublicId(publicOrderId);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "{publicOrderId}"
    )
    public void removeOrderByPublicId(@PathVariable("publicOrderId") String publicOrderId) {

        orderService.deleteOrderByPublicId(publicOrderId);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "{publicOrderId}"
    )
    public Order updateOrderByPublicId(@PathVariable("publicOrderId") @NotNull String publicOrderId, @RequestBody OrderRequest orderRequest) {
        Order order = orderService.getOrderByPublicId(publicOrderId);

        if (order.getRestaurant().getId() != orderRequest.getRestaurantId())
            throw new BadOrderRequestException();

        if (order.getStatus() != 0) {
            throw new OrderCannotBeUpdatedException();
        }

        //if the tableId is updated
        if(order.getTable().getId() != orderRequest.getTableId()) {
            //If table does not belong to this restaurant
            Table table = tableService.getTableById(orderRequest.getTableId());
            if(table.getRestaurant().getId() != orderRequest.getRestaurantId())
                throw new TableNotFoundException();

            order.setTable(table);
        }

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
        return orderService.updateOrder(order);


    }


}
