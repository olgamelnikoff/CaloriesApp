package com.walmart.caloriesApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.walmart.caloriesApp.dtos.FoodDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (columnDefinition = "text")
    private String name;

    @Column
    private int calories;

    @Column (columnDefinition = "text")
    private String description;

    @ManyToMany(fetch=FetchType.LAZY)
    @JsonIgnore
    private List<Meal> mealList = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    private User user;



    public Food (FoodDto foodDto) {
        if (foodDto.getName() != null) {
            this.name = foodDto.getName();
        }

        if (foodDto.getCalories() != 0) {
            this.calories = foodDto.getCalories();
        }
        if (foodDto.getDescription() != null) {
            this.description = foodDto.getDescription();
        }
        if (foodDto.getMealList() != null) {
            this.mealList = foodDto.getMealList();
        }
    }
}
