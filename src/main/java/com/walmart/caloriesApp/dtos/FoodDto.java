package com.walmart.caloriesApp.dtos;

import com.walmart.caloriesApp.entities.Food;
import com.walmart.caloriesApp.entities.Meal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto implements Serializable {

    private Long id;

    private String name;

    private int calories;

    private int finalCalories;

    private String description;

    private List<Meal> mealList = new ArrayList<>();

    private UserDto userDto;

    public FoodDto(Food food) {
        if (food.getId() != null) {
            this.id = food.getId();
        }
        if (food.getName() != null) {
            this.name = food.getName();
        }
        if (food.getCalories() != 0) {
            this.calories = food.getCalories();
        }
        if (food.getDescription() != null) {
            this.description = food.getDescription();
        }
        if (food.getMealList() != null) {
            this.mealList = food.getMealList();
        }
    }
}