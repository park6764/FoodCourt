package com.example.foodcourt;

public class Food {
    private String foodName;
    private int foodPrice;

    Food(String n, int p) {
        foodName = n;
        foodPrice = p;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodPrice() {
        return foodPrice;
    }
}
