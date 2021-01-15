package com.beditsch.project.controller;

import com.beditsch.project.dto.TableRequest;
import com.beditsch.project.exception.AccessDeniedException;
import com.beditsch.project.exception.RestaurantNotFoundException;
import com.beditsch.project.exception.TableRequestInvalidException;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.Table;
import com.beditsch.project.model.User;
import com.beditsch.project.service.RestaurantService;
import com.beditsch.project.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public Table getTableById(@PathVariable("tableId") int tableId) {
        return tableService.getTableById(tableId);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Table createTable (@RequestBody TableRequest tableRequest) {
        if (tableRequest.getRestaurantId() == null)
            throw new TableRequestInvalidException();

        Restaurant restaurant = restaurantService.getRestaurantById(tableRequest.getRestaurantId());
        if (restaurant == null)
            throw new RestaurantNotFoundException();

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> staff = restaurant.getStaff();
        for (User temp : staff) {
            if (temp.getUsername().equals(username)) {
                Table table = new Table();
                table.setRestaurant(restaurant);
                return tableService.createTable(table);
            }
        }
        throw new AccessDeniedException();
    }
}
