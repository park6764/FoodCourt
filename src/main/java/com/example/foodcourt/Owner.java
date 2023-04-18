package com.example.foodcourt;

import java.time.LocalDate;


public class Owner {
    private String name;
    private LocalDate birth;
    private String brand;
    private Auth auth;
    private int profit;
    
    public Owner(String name, LocalDate birth, String brand, Auth auth, int p) {
        this.name = name;
        this.birth = birth;
        this.brand = brand;
        this.auth = auth;
        p = profit;
    }

    public String getOwnerName() {
        return name;
    }

    public LocalDate getOwnerBirth() {
        return birth;
    }

    public String getBrand() {
        return brand;
    }

    public Auth getOwnerAuth() {
        return auth;
    }

    public int getProfit() {
        return profit;
    }

    public int setProfit(int pay) {
        profit += pay;
        return profit;
    }
}
