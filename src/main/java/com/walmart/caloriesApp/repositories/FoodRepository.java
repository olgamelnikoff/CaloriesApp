package com.walmart.caloriesApp.repositories;

import com.walmart.caloriesApp.entities.Food;
import com.walmart.caloriesApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByUserEquals(User user);
}
