package com.meal.mate.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MealServiceTest {
    private val mealService: MealService = MealService()

    @Test
    fun givenStaticList_whenCallingGetMeals_thenStaticListIsReturned() {
        // given

        // when
        val meals = mealService.getMeals()

        // then
        assertTrue(meals.isNotEmpty())
        assertEquals(1, meals.size)
        assertEquals("Knoblauchspaghetti mit frischen Tomaten", meals[0].name)
        assertEquals(4, meals[0].portionSize)
        assertEquals(4, meals[0].portionSize)
        assertTrue(meals[0].ingredients.isNotEmpty())
        assertEquals(1, meals[0].ingredients.size)
        assertEquals("Spaghetti", meals[0].ingredients[0].name)
        assertEquals("500", meals[0].ingredients[0].quantity)
        assertEquals("g", meals[0].ingredients[0].unit)
    }
}