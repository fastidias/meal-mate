package com.meal.mate.controller

import com.meal.mate.BaseValues
import com.meal.mate.model.Meal
import com.meal.mate.service.MealService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping(BaseValues.PATH_MEALS)

// wird gebraucht damit man von dem lokalen Angular auf den Localhost/8080/meal zugreifen kann um sich da die Daten zu ziehen
@CrossOrigin(origins = arrayOf("http://localhost:4200/"))
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