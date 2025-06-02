package com.academy.pizzeria.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("SUSHI")
public class Sushi extends Dishes {
    private int pieces;
}
