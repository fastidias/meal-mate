package com.meal.mate.repo

import com.meal.mate.model.Meal
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface MealRepository: MongoRepository<Meal, String> {
    fun findByName(name: String): Meal
}