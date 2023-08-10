package com.meal.mate.model

data class Meal(val id: String, val directions: String, val name: String, val portionSize: Int, val ingredients: List<Ingredient>)