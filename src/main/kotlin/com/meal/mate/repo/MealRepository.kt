package com.meal.mate.repo

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface MealRepository: MongoRepository<MealItem, String>
