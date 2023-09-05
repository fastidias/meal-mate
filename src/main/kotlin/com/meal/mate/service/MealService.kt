package com.meal.mate.service

import com.meal.mate.model.MealDTO
import com.meal.mate.repo.Meal
import com.meal.mate.repo.MealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MealService(@Autowired val mealRepository: MealRepository) {
    fun getMeals(): List<MealDTO> {
        val mealItems = mealRepository.findAll()
        return mealItems.map { mealItem ->
            MealDTO(
                mealItem.id,
                mealItem.directions,
                mealItem.name,
                1,
                mealItem.ingredients,
                mealItem.imagesource
            )
        }
    }

    fun getMeal(id: String): MealDTO? {
        return getMeals().find { it.id == id }
    }

    fun createMeal(mealDTO: MealDTO): MealDTO {
        mealRepository.save(
            Meal(
                mealDTO.id,
                mealDTO.directions,
                mealDTO.name,
                mealDTO.ingredients,
                mealDTO.imagesource
            )
        )
        return mealDTO
    }

    fun updateMeal(mealDTO: MealDTO): MealDTO {
        mealRepository.findById(mealDTO.id).ifPresent {
            it.name = mealDTO.name
            mealRepository.save(it)
        }
        return mealDTO
    }

    fun deleteMeal(id: String) {
        mealRepository.findById(id).ifPresent {
            mealRepository.delete(it)
        }
    }
}