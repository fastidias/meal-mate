package com.meal.mate.service

import com.meal.mate.model.Meal
import com.meal.mate.repo.MealItem
import com.meal.mate.repo.MealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MealService(@Autowired val mealRepository: MealRepository) {
    fun getMeals(): List<Meal> {
        val mealItems = mealRepository.findAll()
        return mealItems.map { mealItem -> Meal(mealItem.id, mealItem.directions, mealItem.name, 1, mealItem.ingredients, mealItem.imagesource)}
    }

    fun getMeal(id: String): Meal? {
        return getMeals().find { it.id == id }
    }

    fun createMeal(meal: Meal): Meal? {
        mealRepository.save(MealItem(meal.id, meal.directions, meal.name, meal.ingredients, meal.imagesource))
        return meal
    }

    // TODO: get() wieder entfernen
    fun updateMeal(meal: Meal): Meal? {
        return try {
            mealRepository.findById(meal.id).let {
                it.get().directions = meal.directions
                mealRepository.save(it.get())
            }
            meal
        } catch (e: Exception){
            null
        }
    }

    fun deleteMeal(id: String){
        val matchingMeals = mealRepository.findAll().filter { mealItem ->  mealItem.id == id}
        mealRepository.delete(matchingMeals[0])
    }
}