package com.example.foodcourt;

import java.time.LocalDate;
import java.util.ArrayList;

import lombok.*;

@Getter
@AllArgsConstructor
public class Order { // 주문내역
    private String name;
    private String addr;
    private Shop shop;
    private ArrayList<Food> foods; // 비어 있으면 안됨.
    private LocalDate date;

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
