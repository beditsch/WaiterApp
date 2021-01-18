package com.beditsch.project.service;

import com.beditsch.project.exception.OrderCannotBeDeletedException;
import com.beditsch.project.exception.OrderNotFoundException;
import com.beditsch.project.exception.RestaurantNotFoundException;
import com.beditsch.project.exception.TableNotFoundException;
import com.beditsch.project.model.Order;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.Table;
import com.beditsch.project.repository.RestaurantRepository;
import com.beditsch.project.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private OrderService orderService;

    public Table getTableById(Integer tableId){
        Optional<Table> table = Optional.ofNullable(tableRepository.findById(tableId).orElseThrow(TableNotFoundException::new));
        return table.get();
    }

    public Table createTable(Table table) {
        return tableRepository.save(table);
    }

    public void deleteTableById(Integer tableId){
        Optional<Table> table = tableRepository.findById(tableId);
        if (table.isEmpty()) {
            throw new TableNotFoundException();
        }

        tableRepository.deleteById(tableId);
    }

    public Table detachOrders(Table table) {
        List<Order> orders = table.getOrderList();
        if (orders == null) return table;

        for (Order order : orders) {
            order.setTable(null);
            orderService.updateOrder(order);
        }
        table.setOrderList(null);
        return table;
    }

}

