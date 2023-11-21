package com.meal.mate.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class MealData(
    @field:NotBlank(message = "Name must not be blank")
    val name: String,
    val portionSize: Int?,
    @field:NotBlank(message = "Directions must not be blank")
    val directions: String,
    @field:NotEmpty(message = "Ingredients must not be empty")
    val ingredients: List<Ingredient>,
    val source: String?,
    val imageSource: String?
)
