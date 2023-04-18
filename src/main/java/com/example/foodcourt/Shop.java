package com.example.foodcourt;

import java.util.ArrayList;

public class Shop {
    private String shopName;
    private ArrayList<Food> menus;

    Shop(String s, ArrayList<Food> m) {
        shopName = s;
        menus = m;
    }

    public String getShopName() {
        return shopName;
    }

    public ArrayList<Food> getMenus() {
        return menus;
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
