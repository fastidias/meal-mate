package com.meal.mate.service

import com.meal.mate.model.Ingredient
import com.meal.mate.model.Meal
import com.meal.mate.repo.MealRepository
import org.springframework.stereotype.Service

@Service
class MealService(val mealRepository: MealRepository) {
    fun getMeals(): List<Meal> {
        return mealRepository.findAll()
    }
}