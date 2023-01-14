package com.walmart.caloriesApp.services;
import com.walmart.caloriesApp.dtos.MealDto;
import com.walmart.caloriesApp.entities.Meal;
import com.walmart.caloriesApp.entities.User;
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
public class MealServiceImpl {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MealRepository mealRepository;

    @Transactional
    public List<String> addMeal(MealDto mealDto, Long userId) {
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findById(userId);
        Meal meal = new Meal(mealDto);
        userOptional.ifPresent(meal::setUser);
        mealRepository.saveAndFlush(meal);
        response.add("Meal Added Successfully");
        return response;
    }

    @Transactional
    public List<String> deleteMeal(Long mealId) {
        List<String> response = new ArrayList<>();
        Optional <Meal> mealOptional = mealRepository.findById(mealId);
        mealOptional.ifPresent(meal -> mealRepository.delete(meal));
        response.add("Meal Deleted Successfully");
        return response;
    }

    @Transactional
    public List<String> updateMealById(MealDto mealDto) {
        List<String> response = new ArrayList<>();
        Optional <Meal> mealOptional = mealRepository.findById(mealDto.getId());
        mealOptional.ifPresent(meal -> {
            meal.setName(mealDto.getName());
            meal.setDescription(mealDto.getDescription());
            mealRepository.saveAndFlush(meal);
        });
        response.add("Meal Saved Successfully");
        return response;
    }

    @Transactional
    public List<MealDto> getAllMealsByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Meal> mealList = mealRepository.findAllByUserEquals(userOptional.get());
            return mealList.stream().map(meal -> new MealDto(meal)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Transactional
    public Optional<MealDto> getMealById(Long mealId) {
        Optional <Meal> mealOptional = mealRepository.findById(mealId);
        if (mealOptional.isPresent()) {
            return Optional.of(new MealDto(mealOptional.get()));
        }
        return Optional.empty();
    }
}