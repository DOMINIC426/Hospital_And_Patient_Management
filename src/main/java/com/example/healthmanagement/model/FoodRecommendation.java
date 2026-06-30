package com.example.healthmanagement.model;

import com.example.healthmanagement.model.enums.FoodCategory;
import com.example.healthmanagement.model.enums.RecommendationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "food_recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    @Enumerated(EnumType.STRING)
    private FoodCategory category;

    private String localFoodName;

    @Enumerated(EnumType.STRING)
    private RecommendationType recommendationType;
}
