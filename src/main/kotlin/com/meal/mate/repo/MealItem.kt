package com.meal.mate.repo

import com.meal.mate.model.Ingredient
import org.bson.json.JsonObject
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("recipes")
class MealItem(@Id val id: String, var directions: String, var name: String, var ingredients: List<Ingredient>) {
}