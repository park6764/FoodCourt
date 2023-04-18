package com.example.foodcourt;

public class OrderFoodList {
    private MemberInfo info;
    private Food foodName;
    private Food foodPrice;

    OrderFoodList(MemberInfo i, Food n, Food p) {
        info = i;
        foodName = n;
        foodPrice = p;
    }

    public MemberInfo getInfo() {
        return info;
    }

    public Food getFoodName() {
        return foodName;
    }

    public Food getFoodPrice() {
        return foodPrice;
    }
}
