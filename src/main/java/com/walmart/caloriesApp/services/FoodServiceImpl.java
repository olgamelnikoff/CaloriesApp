package com.walmart.caloriesApp.services;

import com.walmart.caloriesApp.dtos.FoodDto;
import com.walmart.caloriesApp.dtos.MealDto;
import com.walmart.caloriesApp.entities.Food;
import com.walmart.caloriesApp.entities.Meal;
import com.walmart.caloriesApp.entities.User;
import com.walmart.caloriesApp.repositories.FoodRepository;
import com.walmart.caloriesApp.repositories.MealRepository;
import com.walmart.caloriesApp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private MealRepository mealRepository;

    @Transactional
    public void addFoodToNewMeal(Long userId, Long foodId, MealDto mealDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        Food food = foodRepository.getById(foodId);
        Meal meal = new Meal(mealDto);
        List<Food> foodList = new ArrayList<>();
        List<Meal> mealList = food.getMealList();
        foodList.add(food);
        meal.setFoodList(foodList);
        mealList.add(meal);
        userOptional.ifPresent(meal::setUser);
        mealRepository.saveAndFlush(meal);
        foodRepository.saveAndFlush(food);
    }

    @Transactional
    public void addFoodToExistingMeal(Long foodId, Long mealId) {
        Optional<Meal> mealOptional = mealRepository.findById(mealId);
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if (mealOptional.isPresent() && foodOptional.isPresent()) {
            Food food = foodOptional.get();
            Meal meal = mealOptional.get();
            List<Food> currentFoodList = meal.getFoodList();
            List<Meal> currentMealList = food.getMealList();
            currentFoodList.add(food);
            currentMealList.add(meal);
            meal.setFoodList(currentFoodList);
            food.setMealList(currentMealList);
            mealRepository.saveAndFlush(meal);
            foodRepository.saveAndFlush(food);
        }
    }

    @Transactional
    public void addFood(FoodDto foodDto, Long userId) {
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findById(userId);
        Food food = new Food(foodDto);
        userOptional.ifPresent(food::setUser);
        foodRepository.saveAndFlush(food);
    }

    @Transactional
    public void deleteFood(Long foodId) {
        List<String> response = new ArrayList<>();
        Optional <Food> foodOptional = foodRepository.findById(foodId);
        foodOptional.ifPresent(food -> foodRepository.delete(food));
    }

    @Transactional
    public void deleteFoodFromMeal(Long mealId, Long foodId) {
        List<String> response = new ArrayList<>();
        Optional<Meal> mealOptional = mealRepository.findById(mealId);
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if (mealOptional.isPresent() && foodOptional.isPresent()) {
            Food food = foodOptional.get();
            Meal meal = mealOptional.get();
            List<Food> currentFoodList = meal.getFoodList();
            List<Meal> currentMealList = food.getMealList();
            currentFoodList.remove(food);
            currentMealList.remove(meal);
            meal.setFoodList(currentFoodList);
            food.setMealList(currentMealList);
            mealRepository.saveAndFlush(meal);
            foodRepository.saveAndFlush(food);
        }
    }

    @Transactional
    public void updateFoodById(FoodDto foodDto) {
        List<String> response = new ArrayList<>();
        Optional <Food> foodOptional = foodRepository.findById(foodDto.getId());
        foodOptional.ifPresent(food -> {
            food.setName(foodDto.getName());
            food.setCalories (foodDto.getCalories());
            food.setDescription(foodDto.getDescription());
            foodRepository.saveAndFlush(food);
            });
    }

    @Transactional
    public Optional<FoodDto> getFoodById(Long foodId) {
        Optional <Food> foodOptional = foodRepository.findById(foodId);

        if (foodOptional.isPresent()) {
            return Optional.of(new FoodDto(foodOptional.get()));
        }
        return Optional.empty();
    }

    @Transactional
    public List<FoodDto> getAllFoodByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Food> foodList = foodRepository.findAllByUserEquals(userOptional.get());
            return foodList.stream().map(food -> new FoodDto(food)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}