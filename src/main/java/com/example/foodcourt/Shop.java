package com.example.foodcourt;

public class Shop {
    private String shopName;

    Shop(String s) {
        shopName = s;
    }

    public String getShopName() {
        return shopName;
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
