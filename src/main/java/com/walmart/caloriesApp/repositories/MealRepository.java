package com.walmart.caloriesApp.repositories;

import com.walmart.caloriesApp.entities.Meal;
import com.walmart.caloriesApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findAllByUserEquals(User user);
}
