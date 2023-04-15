package com.example.foodcourt;

import java.util.ArrayList;

public class Shop {
    private String shopName;
    private Food FoodName;
    private Food FoodPrice;

    Shop(String s) {
        shopName = s;
    }

    Shop(Food n, Food p) {
        FoodName = n;
        FoodPrice = p;
    }

    public String getShopName() {
        return shopName;
    }

    public Food getFoodName() {
        return FoodName;
    }

    public Food getFoodPrice() {
        return FoodPrice;
    }

    // @Override
    // public String toString (ArrayList<Shop> shops) {
    //     if(shops.size() != 0) {
    //         return shopName + ", ";
    //     } else return shopName;
        
    // }

    @Override
    public String toString () {
        return shopName;
    }
}
