package com.meal.mate

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meal.mate.model.Ingredient
import com.meal.mate.repo.MealDBO
import io.micrometer.core.instrument.util.IOUtils

const val MEAL_ID_1 = "4d259eda-8318-463c-9d5f-ed1cd74b2e24"
const val MEAL_NAME_1 = "Knoblauchspaghetti mit frischen Tomaten"
const val MEAL_DIRECTIONS_1 = "Sauerkraut"
const val MEAL_PORTIONSIZE_1 = 1
const val MEAL_SOURCE_1 = "https://www.chefkoch.de/rezepte/168021072969366/Feuriges-Nudelgericht.html?zufall=on"
const val MEAL_IMAGE_URL_1 =
    "https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"
val MEAL_INGREDIENTS_1 = listOf(Ingredient("Spaghetti", "500", "g"))

const val MEAL_ID_2 = "4d259eda-8318-463c-9d5f-ed1cd74b2e25"
const val MEAL_NAME_2 = "Vegane Kicher-Spätzle"
const val MEAL_DIRECTIONS_2 =
    "Die Zutaten für den Teig zusammenrühren und kurz ruhen lassen. Dann über eine Spätzle-Reibe in kochendes Wasser schaben."
const val MEAL_PORTIONSIZE_2 = 1
const val MEAL_IMAGE_URL_2 =
    "https://www.chefkoch.de/rezepte/3461541515680773/Vegane-Kicher-Spaetzle.html"
const val MEAL_SOURCE_2 = "https://www.chefkoch.de/rezepte/168021072969366/Kichererbsen.html?zufall=on"
val MEAL_INGREDIENTS_2 = listOf(Ingredient("Kichererbsen", "40", "g"))

class TestUtils {
    companion object {
        fun loadResource(fileName: String): ObjectNode {
            return this::class.java.getResourceAsStream(fileName).let {
                jacksonObjectMapper().readTree(IOUtils.toString(it)) as ObjectNode
            }
        }

        fun defaultMealDBO1() = MealDBO(MEAL_ID_1, MEAL_NAME_1, MEAL_PORTIONSIZE_1, MEAL_DIRECTIONS_1, MEAL_INGREDIENTS_1, MEAL_SOURCE_1, MEAL_IMAGE_URL_1)
        fun defaultMealDBO2() = MealDBO(MEAL_ID_2, MEAL_NAME_2, MEAL_PORTIONSIZE_2, MEAL_DIRECTIONS_2, MEAL_INGREDIENTS_2, MEAL_SOURCE_2, MEAL_IMAGE_URL_2)
        fun defaultMealDBOList() = listOf(defaultMealDBO1(), defaultMealDBO2())
    }
}
