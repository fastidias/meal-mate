package com.meal.mate.service

import com.meal.mate.model.Meal
import com.meal.mate.repo.MealItem
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
                mealItem.directions,
                mealItem.name,
                1,
                mealItem.ingredients,
                mealItem.imagesource
            )
        }
    }

    fun getMeal(id: String): Meal? {
        return getMeals().find { it.id == id }
    }

    fun createMeal(meal: Meal): Meal {
        mealRepository.save(MealItem(meal.id, meal.directions, meal.name, meal.ingredients, meal.imagesource))
        return meal
    }

    fun updateMeal(meal: Meal): Meal {
        mealRepository.findById(meal.id).ifPresent {
            it.directions = meal.directions
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