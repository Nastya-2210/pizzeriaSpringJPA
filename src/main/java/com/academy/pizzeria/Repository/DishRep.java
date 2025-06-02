package com.academy.pizzeria.Repository;

import com.academy.pizzeria.Model.DishType;
import com.academy.pizzeria.Model.Dishes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRep extends JpaRepository<Dishes, Long> {
    List<Dishes> findByType(DishType type);
}