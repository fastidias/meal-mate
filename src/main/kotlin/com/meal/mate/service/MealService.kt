package com.meal.mate.service

import com.meal.mate.model.Ingredient
import com.meal.mate.model.Meal
import com.meal.mate.repo.MealItem
import com.meal.mate.repo.MealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MealService(@Autowired val mealRepository: MealRepository) {
    fun getMeals(): List<Meal> {
        val newMealItem = MealItem("testId1", "testName1")
        mealRepository.save(newMealItem)
        val mealItems = mealRepository.findAll()
        return mealItems.map { mealItem -> Meal(mealItem.name, 1, ArrayList<Ingredient>()) }
    }
}