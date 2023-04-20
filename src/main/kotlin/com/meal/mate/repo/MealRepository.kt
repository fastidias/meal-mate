package com.meal.mate.repo

import com.meal.mate.model.Meal
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component

@Component
interface MealRepository: MongoRepository<Meal, String> {
    fun findByName(name: String): Meal
}