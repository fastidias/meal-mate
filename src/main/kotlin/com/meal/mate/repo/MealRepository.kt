package com.meal.mate.repo

import org.springframework.data.mongodb.repository.MongoRepository

interface MealRepository: MongoRepository<MealItem, String> {
    /*
    @Query("{name:'?0'}")
    fun findByName(name: String): MealItem
    */
}
