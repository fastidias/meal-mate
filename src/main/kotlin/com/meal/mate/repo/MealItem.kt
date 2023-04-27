package com.meal.mate.repo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("mealitems")
class MealItem(@Id val id: String, val name: String) {
}