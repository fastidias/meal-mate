package com.meal.mate

import com.meal.mate.model.Ingredient
import com.meal.mate.repo.MealEntity

const val MEAL_ID_1 = "4d259eda-8318-463c-9d5f-ed1cd74b2e24"
const val MEAL_NAME_1 = "Knoblauchspaghetti mit frischen Tomaten"
const val MEAL_DIRECTIONS_1 = "Sauerkraut"
const val MEAL_IMAGE_URL_1 =
    "https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"
val MEAL_INGREDIENTS_1 = listOf(Ingredient("Spaghetti", "500", "g"))

const val MEAL_ID_2 = "4d259eda-8318-463c-9d5f-ed1cd74b2e25"
const val MEAL_NAME_2 = "Vegane Kicher-Spätzle"
const val MEAL_DIRECTIONS_2 =
    "Die Zutaten für den Teig zusammenrühren und kurz ruhen lassen. Dann über eine Spätzle-Reibe in kochendes Wasser schaben."
const val MEAL_IMAGE_URL_2 =
    "https://www.chefkoch.de/rezepte/3461541515680773/Vegane-Kicher-Spaetzle.html"
val MEAL_INGREDIENTS_2 = listOf(Ingredient("Kichererbsen", "40", "g"))

// TODO: set realistic values for mealItem examples
open class MealTestBase {
    fun defaultMealItem1() = MealEntity(MEAL_ID_1, MEAL_DIRECTIONS_1, MEAL_NAME_1, MEAL_INGREDIENTS_1, MEAL_IMAGE_URL_1)
    fun defaultMealItem2() = MealEntity(MEAL_ID_2, MEAL_DIRECTIONS_2, MEAL_NAME_2, MEAL_INGREDIENTS_2, MEAL_IMAGE_URL_2)
    fun defaultMealItemList() = listOf(defaultMealItem1(), defaultMealItem2())
}