package com.meal.mate.service

import com.meal.mate.model.Meal
import com.meal.mate.model.MealData
import com.meal.mate.model.MealMeta
import com.meal.mate.repo.MealDBO
import com.meal.mate.repo.MealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MealService(@Autowired val mealRepository: MealRepository) {
    fun getMeals(): List<Meal> {
        val mealItems = mealRepository.findAll()
        return mealItems.map { mealItem ->
            Meal(
                MealData(mealItem.name,
                    mealItem.portionSize,
                    mealItem.directions,
                    mealItem.ingredients,
                    mealItem.source,
                    mealItem.imageSource),
                MealMeta(mealItem.id),
            )
        }
    }

    fun getMeal(id: String): Meal? {
        return getMeals().find { it.meta.id == id }
    }

    fun createMeal(mealData: MealData): Meal {
        val meal = Meal(mealData,MealMeta(UUID.randomUUID().toString()))
        mealRepository.save(
            MealDBO(
                meal.meta.id,
                meal.data.name,
                meal.data.portionSize,
                meal.data.directions,
                meal.data.ingredients,
                meal.data.source,
                meal.data.imageSource
            )
        )

        return meal
    }

    fun updateMeal(meal: Meal): Meal {
        mealRepository.findById(meal.meta.id).ifPresent {
            it.name = meal.data.name
            it.portionSize = meal.data.portionSize
            it.directions = meal.data.directions
            it.ingredients = meal.data.ingredients
            it.source = meal.data.source
            it.imageSource = meal.data.imageSource
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
