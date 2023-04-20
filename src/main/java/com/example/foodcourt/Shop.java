package com.example.foodcourt;

import java.util.ArrayList;
import lombok.*;

@Getter
@AllArgsConstructor
public class Shop {
    private String shopName;
    private ArrayList<Food> menus;

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
