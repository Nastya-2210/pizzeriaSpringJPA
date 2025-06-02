package com.academy.pizzeria.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("PIZZA")
public class Pizza extends Dishes {
    @Enumerated(EnumType.STRING)
    private PizzaSize size;

    private String description;
}

