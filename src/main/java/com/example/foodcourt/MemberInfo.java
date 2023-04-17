package com.example.foodcourt;

import java.time.LocalDate;
import java.util.ArrayList;

public class MemberInfo {
    private String name;
    private LocalDate birth;
    private Auth auth;
    private ArrayList<Food> orderFoodList;

    MemberInfo(String n, LocalDate b, Auth a, ArrayList<Food> o) {
        name = n;
        birth = b;
        auth = a;
        orderFoodList = o;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public Auth getAuth() {
        return auth;
    }
    public ArrayList<Food> getOrderList() {
        return orderFoodList;
    }
}
