package com.meal.mate.controller

import com.meal.mate.PATH_STORED_MEALS
import com.meal.mate.model.StoredMeal
import com.meal.mate.service.StoredMealService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping(PATH_STORED_MEALS)

@CrossOrigin(origins = ["http://localhost:4200/"])
class StoredMealController(val storedMealService: StoredMealService) {
    @GetMapping
    fun getStoredMeals(): ResponseEntity<List<StoredMeal>> {
        return ResponseEntity.ok(storedMealService.getStoredMeals())
    }

    @PostMapping
    fun createStoredMeal(@RequestBody storedMeal: StoredMeal): ResponseEntity<Unit> {
        return storedMealService.createStoredMeal(storedMeal).let { createdMeal ->
            ResponseEntity.created(URI("${PATH_STORED_MEALS}/${createdMeal.id}")).build()
        }
    }

    @GetMapping("/{mealId}")
    fun getStoredMeal(@PathVariable mealId: String): ResponseEntity<StoredMeal> {
        return storedMealService.getStoredMeal(mealId)?.let { meal -> ResponseEntity.ok(meal) } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{mealId}")
    fun updateStoredMeal(@PathVariable mealId: String, @RequestBody storedMeal: StoredMeal): ResponseEntity<Unit> {
        if (storedMeal.id != mealId)
            return ResponseEntity.unprocessableEntity().build()

        storedMealService.updateStoredMeal(storedMeal)

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{mealId}")
    fun deleteStoredMeal(@PathVariable mealId: String): ResponseEntity<Unit> {
        return ResponseEntity(storedMealService.deleteStoredMeal(mealId), HttpStatus.NO_CONTENT)
    }
}