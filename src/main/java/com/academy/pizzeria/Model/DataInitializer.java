package com.academy.pizzeria.Model;

import com.academy.pizzeria.Repository.CustomerRep;
import com.academy.pizzeria.Repository.DishRep;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final CustomerRep customerRepository;
    private final DishRep dishRepository;

    @PostConstruct
    public void init() {
        // Инициализация клиентов
        if (customerRepository.count() == 0) {
            Customers customer1 = new Customers();
            customer1.setName("Иван Иванов");
            customer1.setPhone("+79828347383");
            customer1.setAddress("ул. Пушкина, 6");
            customerRepository.save(customer1);

            Customers customer2 = new Customers();
            customer2.setName("Петр Петров");
            customer2.setPhone("+79151234567");
            customer2.setAddress("ул. Лермонтова, 15");
            customerRepository.save(customer2);
        }

        // Инициализация блюд, если их нет
        if (dishRepository.count() == 0) {
            // Пиццы
            Pizza pizza1 = new Pizza();
            pizza1.setName("Маргарита");
            pizza1.setPrice(450);
            pizza1.setType(DishType.PIZZA);
            pizza1.setSize(PizzaSize.MEDIUM);
            pizza1.setDescription("Классическая пицца с томатным соусом и сыром");
            dishRepository.save(pizza1);

            Pizza pizza2 = new Pizza();
            pizza2.setName("Пепперони");
            pizza2.setPrice(550);
            pizza2.setType(DishType.PIZZA);
            pizza2.setSize(PizzaSize.LARGE);
            pizza2.setDescription("Острая пицца с колбасками пепперони");
            dishRepository.save(pizza2);

            // Суши
            Sushi sushi1 = new Sushi();
            sushi1.setName("Филадельфия");
            sushi1.setPrice(320);
            sushi1.setType(DishType.SUSHI);
            sushi1.setPieces(8);
            dishRepository.save(sushi1);

            Sushi sushi2 = new Sushi();
            sushi2.setName("Калифорния");
            sushi2.setPrice(280);
            sushi2.setType(DishType.SUSHI);
            sushi2.setPieces(6);
            dishRepository.save(sushi2);
        }
    }
}
