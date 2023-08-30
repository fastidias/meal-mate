package com.meal.mate.service

import com.meal.mate.model.StoredMeal
import com.meal.mate.repo.StoredMealItem
import com.meal.mate.repo.StoredMealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StoredMealService(@Autowired val storedMealRepository: StoredMealRepository) {
    fun getStoredMeals(): List<StoredMeal> {
        val storedMealItems = storedMealRepository.findAll()
        return storedMealItems.map { storedMealItem ->
            StoredMeal(
                storedMealItem.id,
                storedMealItem.directions,
                storedMealItem.name,
                1,
                storedMealItem.ingredients,
                storedMealItem.imagesource,
                storedMealItem.date
            )
        }
    }

    fun getStoredMeal(id: String): StoredMeal? {
        return getStoredMeals().find { it.id == id }
    }

    fun createStoredMeal(storedMeal: StoredMeal): StoredMeal {
        storedMealRepository.save(StoredMealItem(storedMeal.id, storedMeal.directions, storedMeal.name, storedMeal.ingredients, storedMeal.imagesource, storedMeal.date))
        return storedMeal
    }

    fun updateStoredMeal(storedMeal: StoredMeal): StoredMeal {
        storedMealRepository.findById(storedMeal.id).ifPresent {
            it.directions = storedMeal.directions
            storedMealRepository.save(it)
        }
        return storedMeal
    }

    fun deleteStoredMeal(id: String) {
        storedMealRepository.findById(id).ifPresent {
            storedMealRepository.delete(it)
        }
    }
}