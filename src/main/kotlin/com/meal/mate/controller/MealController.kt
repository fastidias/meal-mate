package com.meal.mate.controller

import com.meal.mate.model.Meal
import com.meal.mate.service.MealService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MealController(val mealService: MealService) {
    @GetMapping("/meals")
    fun getMeals(): List<Meal> {
        return mealService.getMeals()
    }
}