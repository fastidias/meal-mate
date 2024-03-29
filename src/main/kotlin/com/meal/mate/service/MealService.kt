package com.meal.mate.service

import com.meal.mate.model.Meal
import com.meal.mate.repo.MealDBO
import com.meal.mate.repo.MealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MealService(@Autowired val mealRepository: MealRepository) {
    fun getMeals(): List<Meal> {
        val mealItems = mealRepository.findAll()
        return mealItems.map { mealItem ->
            Meal(
                mealItem.id,
                mealItem.name,
                mealItem.portionSize,
                mealItem.directions,
                mealItem.ingredients,
                mealItem.source,
                mealItem.imageSource
            )
        }
    }

    fun getMeal(id: String): Meal? {
        return getMeals().find { it.id == id }
    }

    fun createMeal(meal: Meal): Meal {
        mealRepository.save(
            MealDBO(
                meal.id,
                meal.name,
                meal.portionSize,
                meal.directions,
                meal.ingredients,
                meal.source,
                meal.imageSource
            )
        )
        return meal
    }

    fun updateMeal(meal: Meal): Meal {
        mealRepository.findById(meal.id).ifPresent {
            it.name = meal.name
            it.portionSize = meal.portionSize
            it.directions = meal.directions
            it.ingredients = meal.ingredients
            it.source = meal.source
            it.imageSource = meal.imageSource
            mealRepository.save(it)
        }
        return meal
    }

    fun deleteMeal(id: String) {
        mealRepository.findById(id).ifPresent {
            mealRepository.delete(it)
        }
    }
}
