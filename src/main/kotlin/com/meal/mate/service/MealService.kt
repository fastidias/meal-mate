package com.meal.mate.service

import com.meal.mate.model.Ingredient
import com.meal.mate.model.Meal
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@Service
class MealService {
    fun getMeals(): List<Meal> {
        val meal = Meal(
            UUID.fromString("2f81508a-69e9-445f-ac82-40418c7bc42f"),
            "Knoblauchspaghetti mit frischen Tomaten",
            4,
            listOf(Ingredient("Spaghetti","500","g")))
        return listOf(meal)
    }

    fun getMeal(id: UUID): Meal? {
        return getMeals().find { it.id == id }
    }

    fun createMeal(meal: Meal): Meal? {
        return meal
    }

    fun deleteMeal(id: UUID){
        // returns nothing, delete meal by id
    }
}