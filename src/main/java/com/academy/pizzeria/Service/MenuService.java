package com.academy.pizzeria.Service;

import com.academy.pizzeria.Model.DishType;
import com.academy.pizzeria.Model.Dishes;
import com.academy.pizzeria.Model.Pizza;
import com.academy.pizzeria.Model.Sushi;
import com.academy.pizzeria.Repository.DishRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final DishRep dishRepository;

    public void printFullMenu() {
        System.out.println("\n=== ПОЛНОЕ МЕНЮ ===");
        dishRepository.findAll().forEach(this::printDishDetails);
    }

    public void printMenuByType(DishType type) {
        System.out.printf("%n=== МЕНЮ (%s) ===%n", type);
        dishRepository.findByType(type).forEach(this::printDishDetails);
    }

    private void printDishDetails(Dishes dish) {
        if (dish instanceof Pizza pizza) {
            System.out.printf("%s (Пицца) - %.2f руб. | Размер: %s | %s%n",
                    pizza.getName(), pizza.getPrice(), pizza.getSize(), pizza.getDescription());
        } else if (dish instanceof Sushi sushi) {
            System.out.printf("%s (Суши) - %.2f руб. | %d шт.%n",
                    sushi.getName(), sushi.getPrice(), sushi.getPieces());
        }
    }
}
