package com.meal.mate.controller

import com.meal.mate.model.Meal
import com.meal.mate.service.MealService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/meals")
class MealController(val mealService: MealService) {
    @GetMapping
    fun getMeals(): List<Meal> {
        return mealService.getMeals()
    }

    @PutMapping
    fun createMeal(@RequestBody meal: Meal): ResponseEntity<Meal> {
        val createdMeal = mealService.createMeal(meal)
        val uri = URI("/meals/${createdMeal.id}")
        return ResponseEntity.created(uri).build()
    }

    @GetMapping("/{mealId}")
    fun getMeal(@PathVariable mealId: UUID): ResponseEntity<Meal> {
        val meal = mealService.getMeal(mealId)
        return if (meal != null) ResponseEntity.ok(meal) else ResponseEntity.notFound().build()
    }
}