package com.example.foodcourt.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import lombok.*;

@Getter
@AllArgsConstructor
public class Member {
    private String name;
    private LocalDate birth;
    private String addr;
    private Auth auth;
    private int money = 0;
    private ArrayList<Order> orders;

    public boolean pay(int pay) {
        if(money >= pay) {
            money -= pay;
            return true;
        } else {
            return false; 
        }
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
