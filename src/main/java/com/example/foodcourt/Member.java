package com.example.foodcourt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

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
            return false; // 안전장치가 없을까..?
        }
    }
}
