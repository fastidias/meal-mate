package com.meal.mate.controller

import com.meal.mate.PATH_MEALS
import com.meal.mate.model.MealDTO
import com.meal.mate.service.MealService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping(PATH_MEALS)

@CrossOrigin(origins = ["http://localhost:4200/"])
class MealController(val mealService: MealService) {
    @GetMapping
    fun getMeals(): ResponseEntity<List<MealDTO>> {
        return ResponseEntity.ok(mealService.getMeals())
    }

    @PostMapping
    fun createMeal(@RequestBody mealDTO: MealDTO): ResponseEntity<Unit> {
        return mealService.createMeal(mealDTO).let { createdMeal ->
            ResponseEntity.created(URI("${PATH_MEALS}/${createdMeal.id}")).build()
        }
    }

    @GetMapping("/{mealId}")
    fun getMeal(@PathVariable mealId: String): ResponseEntity<MealDTO> {
        return mealService.getMeal(mealId)?.let { meal -> ResponseEntity.ok(meal) } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{mealId}")
    fun updateMeal(@PathVariable mealId: String, @RequestBody mealDTO: MealDTO): ResponseEntity<Unit> {
        if (mealDTO.id != mealId)
            return ResponseEntity.unprocessableEntity().build()

        mealService.updateMeal(mealDTO)

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{mealId}")
    fun deleteMeal(@PathVariable mealId: String): ResponseEntity<Unit> {
        return ResponseEntity(mealService.deleteMeal(mealId), HttpStatus.NO_CONTENT)
    }
}