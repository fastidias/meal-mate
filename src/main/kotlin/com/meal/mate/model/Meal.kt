package com.meal.mate.model

data class Meal(
    val id: String,
    val name: String,
    val portionSize: Int,
    val directions: String,
    val ingredients: List<Ingredient>,
    val imagesource: String
)