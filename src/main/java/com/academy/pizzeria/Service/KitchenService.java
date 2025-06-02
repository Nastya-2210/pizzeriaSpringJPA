package com.academy.pizzeria.Service;

import com.academy.pizzeria.Model.OrderStatus;
import com.academy.pizzeria.Model.Orders;
import com.academy.pizzeria.Repository.OrderRep;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KitchenService {
    @Value("${kitchen.capacity:5}")
    private int capacity;

    @Value("${kitchen.timeout:5000}")
    private long timeout;

    private final OrderRep orderRepository;
    private int currentOrders = 0;

    public synchronized boolean canAcceptOrder() {
        return currentOrders < capacity;
    }

    public synchronized void prepareOrder(Orders order) {

        if (!canAcceptOrder()) {
            throw new IllegalStateException("Кухня перегружена. Заказ отклонен.");
        }

        currentOrders++;
        order.setStatus(OrderStatus.IN_PROGRESS);
        orderRepository.save(order);

        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            currentOrders--;
            order.setStatus(OrderStatus.REJECTED);
            orderRepository.save(order);
            throw new RuntimeException("Order preparation interrupted", e);
        }

        currentOrders--;
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }
}