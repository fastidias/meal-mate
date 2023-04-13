package com.meal.mate.service

import com.meal.mate.model.Ingredient
import com.meal.mate.model.Meal
import org.springframework.stereotype.Service

@Service
class MealService {
    fun getMeals(): List<Meal> {
        val meal = Meal(
            "Knoblauchspaghetti mit frischen Tomaten",
            4,
            listOf(Ingredient("Spaghetti","500","g")))
        return listOf(meal)
    }
}