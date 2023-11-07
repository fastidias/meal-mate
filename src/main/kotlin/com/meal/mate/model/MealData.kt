package com.meal.mate.model

data class MealData(
    val name: String,
    val portionSize: Int,
    val directions: String,
    val ingredients: List<Ingredient>,
    val source: String,
    val imageSource: String
)
