package com.meal.mate.controller

import com.meal.mate.BaseValues
import com.meal.mate.model.Meal
import com.meal.mate.service.MealService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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

    @PostMapping
    fun createMeal(@RequestBody meal: Meal): ResponseEntity<Meal> {
        return mealService.createMeal(meal)?.let { createdMeal ->
            ResponseEntity.created(URI("${BaseValues.PATH_MEALS}/${createdMeal.id}")).build()
        } ?: ResponseEntity.internalServerError().build()
    }

    @GetMapping("/{mealId}")
    fun getMeal(@PathVariable mealId: String): ResponseEntity<Meal> {
        return mealService.getMeal(mealId)?.let { meal -> ResponseEntity.ok(meal) } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{mealId}")
    fun updateMeal(@PathVariable mealId: String, @RequestBody meal: Meal): ResponseEntity<Void>{
        if(meal.id != mealId)
            return ResponseEntity.unprocessableEntity().build()

        mealService.updateMeal(meal)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{mealId}")
    fun deleteMeal(@PathVariable mealId: String): ResponseEntity<Unit> {
        return ResponseEntity(mealService.deleteMeal(mealId), HttpStatus.NO_CONTENT)
    }
}