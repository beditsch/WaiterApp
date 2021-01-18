package com.beditsch.project.controller;

import com.beditsch.project.dto.TableRequest;
import com.beditsch.project.exception.AccessDeniedException;
import com.beditsch.project.exception.RestaurantNotFoundException;
import com.beditsch.project.exception.TableRequestInvalidException;
import com.beditsch.project.model.Meal;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.Table;
import com.beditsch.project.model.User;
import com.beditsch.project.service.RestaurantService;
import com.beditsch.project.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("table")
public class TableController {

    @Autowired
    private TableService tableService;
    @Autowired
    private RestaurantService restaurantService;


    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{tableId}"
    )
    public Table getTableById(@PathVariable("tableId") @NotNull int tableId) {

        Table table = tableService.getTableById(tableId);

        if(!restaurantService.checkOwnership(table.getRestaurant()))
            throw new AccessDeniedException();

        return table;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Table createTable (@Valid @RequestBody TableRequest tableRequest) {

        Restaurant restaurant = restaurantService.getRestaurantById(tableRequest.getRestaurantId());

        if(!restaurantService.checkOwnership(restaurant))
            throw new AccessDeniedException();

        Table table = new Table();
        table.setRestaurant(restaurant);
        return tableService.createTable(table);

    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "{tableId}"
    )
    public void removeTableById(@PathVariable("tableId") @NotNull int tableId) {
        Table table = getTableById(tableId);

        if(!restaurantService.checkOwnership(table.getRestaurant()))
            throw new AccessDeniedException();

        table = tableService.detachOrders(table);
        tableService.deleteTableById(table.getId());
    }
}
