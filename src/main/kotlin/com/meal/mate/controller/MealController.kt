package com.meal.mate.controller

import com.meal.mate.BaseValues
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
@RequestMapping(BaseValues.PATH_MEALS)
class MealController(val mealService: MealService) {
    @GetMapping
    fun getMeals(): List<Meal> {
        return mealService.getMeals()
    }

    @PutMapping
    fun createMeal(@RequestBody meal: Meal): ResponseEntity<Meal> {
        return mealService.createMeal(meal)?.let { createdMeal ->
            ResponseEntity.created(URI("${BaseValues.PATH_MEALS}/${createdMeal.id}")).build()
        } ?: ResponseEntity.internalServerError().build()
    }

    @GetMapping("/{mealId}")
    fun getMeal(@PathVariable mealId: UUID): ResponseEntity<Meal> {
        return mealService.getMeal(mealId)?.let { meal -> ResponseEntity.ok(meal) } ?: ResponseEntity.notFound().build()
    }
}