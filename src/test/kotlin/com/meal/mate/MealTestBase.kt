package com.meal.mate

import com.meal.mate.model.Ingredient
import com.meal.mate.repo.MealItem

const val MEAL_ID = "4d259eda-8318-463c-9d5f-ed1cd74b2e24"
const val MEAL_NAME = "Knoblauchspaghetti mit frischen Tomaten"
const val MEAL_DIRECTIONS = "Sauerkraut"
const val MEAL_IMAGE_URL =
    "https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"
val MEAL_INGREDIENTS = listOf(Ingredient("Spaghetti", "500", "g"))

open class MealTestBase() {
    fun defaultMealItem() = MealItem(MEAL_ID, MEAL_DIRECTIONS, MEAL_NAME, MEAL_INGREDIENTS, MEAL_IMAGE_URL)
}