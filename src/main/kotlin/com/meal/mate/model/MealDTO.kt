package com.meal.mate.model

data class MealDTO(
    val id: String,
    val directions: String,
    val name: String,
    val portionSize: Int,
    val ingredients: List<Ingredient>,
    var imagesource: String
)