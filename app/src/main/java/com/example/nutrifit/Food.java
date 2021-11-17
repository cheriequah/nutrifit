package com.example.nutrifit;

public class Food {
    String foodName, calories;

    public Food(String foodName, String calories) {
        this.foodName = foodName;
        this.calories = calories;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getCalories() {
        return calories;
    }
}
