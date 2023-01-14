package com.walmart.caloriesApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.walmart.caloriesApp.dtos.MealDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "meals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (columnDefinition = "text")
    private String name;

    @Column (columnDefinition = "text")
    private String description;

    @ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
    @JsonIgnore
    private List<Food> foodList = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    private User user;

    public Meal(MealDto mealDto) {
        if (mealDto.getId() != null) {
            this.id = mealDto.getId();
        }
        if (mealDto.getName() != null) {
            this.name = mealDto.getName();
        }
        if (mealDto.getDescription() != null) {
            this.description = mealDto.getDescription();
        }
        if (mealDto.getFoodList() != null) {
            this.foodList = mealDto.getFoodList();
        }
    }
}
