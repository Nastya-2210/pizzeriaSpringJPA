package com.academy.pizzeria.Repository;
import com.academy.pizzeria.Model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRep extends JpaRepository<Customers, Long> {
    Customers findByName(String name);
}
