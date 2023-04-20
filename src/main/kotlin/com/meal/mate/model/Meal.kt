package com.meal.mate.model

import java.util.*

data class Meal(val id: UUID, val name: String, val portionSize: Int, val ingredients: List<Ingredient>)