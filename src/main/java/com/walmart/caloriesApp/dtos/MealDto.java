package com.walmart.caloriesApp.dtos;

import com.walmart.caloriesApp.entities.Food;
import com.walmart.caloriesApp.entities.Meal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDto implements Serializable {

    private Long id;

    private String name;

    private String description;

    private List<Food> foodList;

    private UserDto userDto;

    public MealDto(Meal meal) {
        if (meal.getId() != null) {
            this.id = meal.getId();
        }
        if (meal.getName() != null) {
            this.name = meal.getName();
        }
        if (meal.getDescription() != null) {
            this.description = meal.getDescription();
        }
        if (meal.getFoodList() != null) {
            this.foodList = meal.getFoodList();
        }
    }
}
