package com.beditsch.project.controller;

import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.Table;
import com.beditsch.project.service.RestaurantService;
import com.beditsch.project.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("table")
public class TableController {

    @Autowired
    private TableService tableService;


    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{tableId}"
    )
    public Table getTableById(@PathVariable("tableId") int tableId) {
        return tableService.getTableById(tableId);
    }
}
