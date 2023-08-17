package com.meal.mate.repo

import org.springframework.data.mongodb.repository.MongoRepository

interface MealRepository: MongoRepository<MealItem, String>
