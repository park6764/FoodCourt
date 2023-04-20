package com.example.foodcourt.models;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Food {
    private String foodName;
    private int foodPrice;
}
