package com.example.foodcourt;

import java.time.LocalDate;
import java.util.ArrayList;

import lombok.*;

@Getter
@AllArgsConstructor
public class Owner {
    private String name;
    private LocalDate birth;
    private Shop shop;
    private Auth auth;
    private int profit;
    private ArrayList<Order> orders; // 비어있으면 안됨. 

    public void addProfit(int pay) {
        profit += pay;
    }
}
