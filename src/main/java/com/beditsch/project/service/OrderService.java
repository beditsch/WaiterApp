package com.beditsch.project.service;

import com.beditsch.project.exception.OrderCannotBeDeletedException;
import com.beditsch.project.exception.OrderNotFoundException;
import com.beditsch.project.model.Order;
import com.beditsch.project.repository.OrderRepository;
import com.beditsch.project.shared.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private Utility utility;

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

    public Order getOrderByPublicId(String publicOrderId){
        Optional<Order> order = Optional.ofNullable(orderRepository.findByOrderId(publicOrderId));
        if (order.isPresent())
            return order.get();
        else
            throw new OrderNotFoundException();
    }

    public void deleteOrderById(Integer orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new OrderNotFoundException();
        }
        else if (order.get().getStatus() != 0) {
            throw new OrderCannotBeDeletedException();
        }
        orderRepository.deleteById(orderId);
    }

    public void deleteOrderByPublicId(String publicOrderId){
        Optional<Order> order = Optional.ofNullable(orderRepository.findByOrderId(publicOrderId));
        if (order.isEmpty()) {
            throw new OrderNotFoundException();
        }
        else if (order.get().getStatus() != 0) {
            throw new OrderCannotBeDeletedException();
        }
        orderRepository.delete(order.get());
    }


    public Order createOrder(Order order) {
        order.setOrderId(utility.generateOrderId(30));
        return orderRepository.save(order);
    }


    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }



}
