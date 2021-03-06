package com.beditsch.project.controller;

import com.beditsch.project.dto.OrderPositionRequest;
import com.beditsch.project.dto.OrderRequest;
import com.beditsch.project.dto.OrderStatusUpdateRequest;
import com.beditsch.project.exception.*;
import com.beditsch.project.model.*;
import com.beditsch.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private UserService userService;


    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Order createNewOrder(@Valid @RequestBody OrderRequest orderRequest) {

        //If  restaurant does not exist
        if (!restaurantService.restaurantExists(orderRequest.getRestaurantId()))
            throw new RestaurantNotFoundException();

        //If table does not belong to this restaurant
        Table table = tableService.getTableById(orderRequest.getTableId());
        if (table.getRestaurant().getId() != orderRequest.getRestaurantId())
            throw new TableNotFoundException();

        Order order = new Order();
        order.setTable(table);

        OrderPosition tempOrderPosition;
        Meal tempMeal;
        List<OrderPosition> fullOrderPositionsList = new ArrayList<>();
        for (OrderPositionRequest temp : orderRequest.getOrderPositionsList()) {
            tempOrderPosition = new OrderPosition();
            tempMeal = mealService.getMealById(temp.getMealId());

            if ((tempMeal.getRestaurant().getId() != orderRequest.getRestaurantId()) || !tempMeal.isAvailable())
                throw new MealNotFoundException();

            tempOrderPosition.setMeal(tempMeal);
            tempOrderPosition.setOrder(order);
            tempOrderPosition.setQuantity(temp.getQuantity());

            fullOrderPositionsList.add(tempOrderPosition);
        }

        order.setOrderPositions(fullOrderPositionsList);
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
    public void removeOrderByPublicId(@PathVariable("publicOrderId") @NotNull String publicOrderId) {

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
        if (order.getTable().getId() != orderRequest.getTableId()) {
            //If table does not belong to this restaurant
            Table table = tableService.getTableById(orderRequest.getTableId());
            if (table.getRestaurant().getId() != orderRequest.getRestaurantId())
                throw new TableNotFoundException();

            order.setTable(table);
        }

        OrderPosition tempOrderPosition;
        Meal tempMeal;
        List<OrderPosition> fullOrderPositionsList = new ArrayList<>();
        for (OrderPositionRequest temp : orderRequest.getOrderPositionsList()) {
            tempOrderPosition = new OrderPosition();
            tempMeal = mealService.getMealById(temp.getMealId());

            if ((tempMeal.getRestaurant().getId() != orderRequest.getRestaurantId()) || !tempMeal.isAvailable())
                throw new MealNotFoundException();

            tempOrderPosition.setMeal(tempMeal);
            tempOrderPosition.setOrder(order);
            tempOrderPosition.setQuantity(temp.getQuantity());

            fullOrderPositionsList.add(tempOrderPosition);
        }

        order.setOrderPositions(fullOrderPositionsList);
        return orderService.updateOrder(order);

    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "status/{orderId}"
    )
    public Order updateOrderStatus (@RequestBody @Valid OrderStatusUpdateRequest orderStatusUpdateRequest, @PathVariable @NotNull Integer orderId) {
        Order order = orderService.getOrderById(orderId);
        if (!restaurantService.checkOwnership(order.getRestaurant()))
            throw new AccessDeniedException();

        order.setStatus(orderStatusUpdateRequest.getOrderStatus());
        return orderService.updateOrder(order);
    }



}
