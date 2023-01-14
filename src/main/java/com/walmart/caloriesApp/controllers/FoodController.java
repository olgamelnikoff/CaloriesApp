package com.walmart.caloriesApp.controllers;

import com.walmart.caloriesApp.dtos.FoodDto;
import com.walmart.caloriesApp.dtos.MealDto;
import com.walmart.caloriesApp.services.FoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/food")
public class FoodController {

    @Autowired
    private FoodServiceImpl foodService;

    @GetMapping("/user/{userId}")
    public List<FoodDto> getAllFoodByUserId (@PathVariable Long userId) {
        return foodService.getAllFoodByUserId(userId);
    }

    @PostMapping("/user/{userId}")
    public void addFood (@RequestBody FoodDto foodDto, @PathVariable Long userId) {
        foodService.addFood(foodDto, userId);
    }

    @DeleteMapping("/{foodId}")
    public void deleteFoodById (@PathVariable Long foodId) {
        foodService.deleteFood(foodId);
    }

    @PutMapping
    public void updateFood (@RequestBody FoodDto foodDto) {
        foodService.updateFoodById(foodDto);
    }

    @GetMapping("/{foodId}")
    public Optional<FoodDto> getFoodById (@PathVariable Long foodId) {
        return foodService.getFoodById(foodId);
    }

    @PostMapping("/meal/{mealId}/{foodId}")
    public void addFoodToExistingMeal (@PathVariable Long foodId, @PathVariable Long mealId) {
         foodService.addFoodToExistingMeal(foodId, mealId);
    }

    @PostMapping("/user/{userId}/{foodId}")
    public void addFoodToNewMeal (@PathVariable Long userId, @PathVariable Long foodId, @RequestBody MealDto mealDto) {
        foodService.addFoodToNewMeal(userId, foodId, mealDto);
    }

    @DeleteMapping("/meal/{mealId}/{foodId}")
    public void removeFoodFromMeal (@PathVariable Long mealId, @PathVariable Long foodId) {
        foodService.deleteFoodFromMeal(mealId, foodId);
    }
}