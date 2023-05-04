package com.meal.mate.service

import com.meal.mate.model.Ingredient
import com.meal.mate.model.Meal
import com.meal.mate.repo.MealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MealService(@Autowired val mealRepository: MealRepository) {
    fun getMeals(): List<Meal> {
        val mealItems = mealRepository.findAll()
        return mealItems.map { mealItem -> Meal(mealItem.name, 1, ArrayList()) }
    }

    fun getMeal(id: UUID): Meal? {
        return getMeals().find { it.id == id }
    }

    fun createMeal(meal: Meal): Meal? {
        return meal
    }
}