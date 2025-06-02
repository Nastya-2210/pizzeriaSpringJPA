package com.academy.pizzeria.Repository;

import com.academy.pizzeria.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRep extends JpaRepository<Orders, Long> {
    List<Orders> findByCustomerId(Long customerId);
}
