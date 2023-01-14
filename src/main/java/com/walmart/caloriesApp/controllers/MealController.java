package com.walmart.caloriesApp.controllers;

import com.walmart.caloriesApp.dtos.MealDto;
import com.walmart.caloriesApp.services.MealServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/meal")
public class MealController {
    @Autowired
    private MealServiceImpl mealService;

    @GetMapping("/user/{userId}")
    public List<MealDto> getAllMealByUserId (@PathVariable Long userId) {
        return mealService.getAllMealsByUserId(userId);
    }

    @PostMapping("/user/{userId}")
    public void addMeal (@RequestBody MealDto mealDto, @PathVariable Long userId) {
        mealService.addMeal(mealDto, userId);
    }

    @DeleteMapping("/{mealId}")
    public void deleteMealById (@PathVariable Long mealId) {
        mealService.deleteMeal(mealId);
    }

    @PutMapping
    public void updateMeal (@RequestBody MealDto mealDto) {
        mealService.updateMealById(mealDto);
    }

    @GetMapping("/{mealId}")
    public Optional<MealDto> getMealById (@PathVariable Long mealId) {
        return mealService.getMealById(mealId);
    }
}