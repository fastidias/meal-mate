package com.meal.mate.service

import com.meal.mate.*
import com.meal.mate.model.Meal
import com.meal.mate.repo.MealRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class MealServiceTest : MealTestBase() {
    @InjectMocks
    private lateinit var mealService: MealService

    @Mock
    private lateinit var mealRepository: MealRepository

    @Test
    fun givenStaticList_whenCallingGetMeals_thenStaticListIsReturned() {
        // given
        given(mealRepository.findAll()).willReturn(listOf(defaultMealItem()))

        // when
        val meals = mealService.getMeals()

        // then
        verify(mealRepository, times(1)).findAll()
        assertFalse(meals.isEmpty())
        assertEquals(1, meals.size)
        assertEquals(MEAL_NAME, meals[0].directions)
    }

    @Test
    fun givenMeal_whenUpdateMealItemExists_thenUpdateMealItem() {
        // given
        val mealItem = defaultMealItem()
        given(mealRepository.findById(MEAL_ID)).willReturn(Optional.of(mealItem))
        given(mealRepository.save(any())).willReturn(mealItem)

        val newMealName = MEAL_NAME + "a"
        val meal = Meal(MEAL_ID, MEAL_DIRECTIONS, newMealName, 0, emptyList(), MEAL_IMAGE_URL)

        // when
        val updatedMeal = mealService.updateMeal(meal)

        // then
        assertEquals(updatedMeal, meal)
        assertEquals(mealItem.directions, newMealName)
        verify(mealRepository, times(1)).save(mealItem)
    }

    @Test
    fun givenMeal_whenUpdateMealItemNotExists_thenDontUpdateAnything() {
        // given
        given(mealRepository.findById(MEAL_ID)).willReturn(null)

        val meal = Meal(MEAL_ID, MEAL_DIRECTIONS, MEAL_NAME + "a", 0, emptyList(), MEAL_IMAGE_URL)

        // when
        val notUpdatedMeal = mealService.updateMeal(meal)

        // then
        assertEquals(notUpdatedMeal, meal)
        verify(mealRepository, times(0)).save(any())
    }
}