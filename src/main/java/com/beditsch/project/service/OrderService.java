package com.beditsch.project.service;

import com.beditsch.project.dao.OrderDao;
import com.beditsch.project.dao.RestaurantDao;
import com.beditsch.project.exception.OrderNotFoundException;
import com.beditsch.project.model.Order;
import com.beditsch.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent())
            return order.get();
        else
            throw new OrderNotFoundException();
    }

    public void deleteOrderById(Integer orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            orderRepository.deleteById(orderId);
            return;
        }
        throw new OrderNotFoundException();
    }

    public Order createNewOrder(Order order) {
        return orderRepository.save(order);
    }



}
