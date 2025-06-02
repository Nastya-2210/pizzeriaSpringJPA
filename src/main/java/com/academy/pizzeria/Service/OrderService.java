package com.academy.pizzeria.Service;

import com.academy.pizzeria.Model.*;
import com.academy.pizzeria.Repository.CustomerRep;
import com.academy.pizzeria.Repository.DishRep;
import com.academy.pizzeria.Repository.OrderRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRep orderRepository;
    private final CustomerRep customerRepository;
    private final DishRep dishRepository;
    private final KitchenService kitchenService;

    @Transactional
    public Orders placeOrder(List<Long> dishIds, Long customerId) {
        if (dishIds == null || dishIds.isEmpty()) {
            throw new IllegalArgumentException("Нельзя разместить пустой заказ.");
        }

        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Клиент с id " + customerId + " не найден."));

        List<Dishes> dishes = dishRepository.findAllById(dishIds);

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setDishes(dishes);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);

        Orders savedOrder = orderRepository.save(order);

        try {
            if (kitchenService.canAcceptOrder()) {
                kitchenService.prepareOrder(savedOrder);
            } else {
                savedOrder.setStatus(OrderStatus.REJECTED);
                orderRepository.save(savedOrder);
            }
        } catch (Exception e) {
            savedOrder.setStatus(OrderStatus.REJECTED);
            orderRepository.save(savedOrder);
            throw e;
        }

        return savedOrder;
    }

    public List<Orders> getCustomerOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public void printOrderDetails(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        System.out.println("\n=== ДЕТАЛИ ЗАКАЗА #" + order.getId() + " ===");
        System.out.println("Клиент: " + order.getCustomer().getName());
        System.out.println("Дата/время: " + order.getOrderTime());
        System.out.println("Статус: " + order.getStatus());
        System.out.println("Блюда:");
        order.getDishes().forEach(dish -> {
            if (dish instanceof Pizza pizza) {
                System.out.printf("  - %s (Пицца, %s) - %.2f руб.%n",
                        pizza.getName(), pizza.getSize(), pizza.getPrice());
            } else if (dish instanceof Sushi sushi) {
                System.out.printf("  - %s (Суши, %d шт.) - %.2f руб.%n",
                        sushi.getName(), sushi.getPieces(), sushi.getPrice());
            }
        });
        System.out.println("Итого: " +
                order.getDishes().stream().mapToDouble(Dishes::getPrice).sum() + " руб.");
    }
}
