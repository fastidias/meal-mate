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
        return mealItems.map { mealItem -> Meal(mealItem.id, mealItem.name, 1, ArrayList()) }
    }

    fun getMeal(id: UUID): Meal? {
        return getMeals().find { it.id == id }
    }

    fun createMeal(meal: Meal): Meal? {
        mealRepository.save(MealItem(meal.id, meal.name))
        return meal
    }

    fun updateMeal(meal: Meal): Meal? {
        mealRepository.findById(meal.id)?.let {
            it.name = meal.name
            mealRepository.save(it)
        }
        return meal
    }

    fun deleteMeal(id: UUID){
        val matchingMeals = mealRepository.findAll().filter { mealItem ->  mealItem.id == id}
        mealRepository.delete(matchingMeals[0])
    }
}