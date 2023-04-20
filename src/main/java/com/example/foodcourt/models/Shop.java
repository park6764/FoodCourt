package com.example.foodcourt.models;

import java.util.ArrayList;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Shop {
    private String shopName;
    private ArrayList<Food> menus;

    /*
     * {
     *  "shopName": "ABC",
     *  "menus": [
     *      {
     *          "foodName": "Hot Dog",
     *          "foodPrice": 3000
     *      }
     *  ]
     * }
     */

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
