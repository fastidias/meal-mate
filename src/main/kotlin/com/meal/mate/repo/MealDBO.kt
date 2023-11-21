package com.meal.mate.repo

import com.meal.mate.model.Ingredient
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("recipes")
class MealDBO(
    @Id val id: String,
    var name: String,
    var portionSize: Int?,
    var directions: String,
    var ingredients: List<Ingredient>,
    var source: String?,
    var imageSource: String?
)
