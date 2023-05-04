package com.meal.mate.repo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("mealitems")
class MealItem(@Id val id: UUID, val name: String) {
}