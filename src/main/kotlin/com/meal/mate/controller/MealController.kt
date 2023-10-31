package com.meal.mate.controller

import com.meal.mate.PATH_MEALS
import com.meal.mate.model.Meal
import com.meal.mate.service.MealService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping(PATH_MEALS)

@CrossOrigin(origins = ["http://localhost:4200/"])
class MealController(val mealService: MealService) {
    @GetMapping
    fun getMeals(): ResponseEntity<List<Meal>> {
        return ResponseEntity.ok(mealService.getMeals())
    }

    @PostMapping
    fun createMeal(@Valid @RequestBody meal: Meal): ResponseEntity<Unit> {
        return mealService.createMeal(meal).let { createdMeal ->
            ResponseEntity.created(URI("${PATH_MEALS}/${createdMeal.id}")).build()
        }
    }

    @GetMapping("/{mealId}")
    fun getMeal(@PathVariable mealId: String): ResponseEntity<Meal> {
        return mealService.getMeal(mealId)?.let { meal -> ResponseEntity.ok(meal) } ?: throw NoSuchElementException("No Meal with Id $mealId found.")
    }

    @PutMapping("/{mealId}")
    fun updateMeal(@PathVariable mealId: String, @RequestBody meal: Meal): ResponseEntity<Unit> {
        if (meal.id != mealId)
            return ResponseEntity.unprocessableEntity().build()

        mealService.updateMeal(meal)

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{mealId}")
    fun deleteMeal(@PathVariable mealId: String): ResponseEntity<Unit> {
        return ResponseEntity(mealService.deleteMeal(mealId), HttpStatus.NO_CONTENT)
    }
}
