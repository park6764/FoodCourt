package com.example.foodcourt;

import java.time.LocalDate;
import java.util.ArrayList;

public class MemberInfo {
    private String name;
    private LocalDate birth;
    private String addr;
    private Auth auth;
    private int money = 0;
    private ArrayList<OrderFoodList> orderFoodList;

    MemberInfo(String n, LocalDate b, String ad, Auth a, int m ,ArrayList<OrderFoodList> o) {
        name = n;
        birth = b;
        addr = ad;
        auth = a;
        money = m;
        orderFoodList = o;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public String getAddr() {
        return addr;
    }

    public Auth getAuth() {
        return auth;
    }

    public int getMoney() {
        return money;
    }

    public int setMoney(int pay) {
        if(money >= pay) {
            money -= pay;
            return money;
        } else {
            return money; // 안전장치가 없을까..?
        }
    }

    public ArrayList<OrderFoodList> getOrderList() {
        return orderFoodList;
    }
}
