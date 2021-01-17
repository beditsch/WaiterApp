package com.beditsch.project.repository;

import com.beditsch.project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByOrderId (String publicOrderId);

}
