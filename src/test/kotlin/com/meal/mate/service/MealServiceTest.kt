package com.meal.mate.service

import com.meal.mate.*
import com.meal.mate.TestUtils.Companion.defaultMealDBO1
import com.meal.mate.TestUtils.Companion.defaultMealDBOList
import com.meal.mate.model.Meal
import com.meal.mate.repo.MealRepository
import org.junit.jupiter.api.Assertions.*
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
class MealServiceTest {
    @InjectMocks
    private lateinit var mealService: MealService

    @Mock
    private lateinit var mealRepository: MealRepository

    @Test
    fun givenMealEntities_whenCallingGetMeals_thenMealEntitiesAreReturned() {
        // given
        given(mealRepository.findAll()).willReturn(defaultMealDBOList())

        // when
        val meals = mealService.getMeals()

        // then
        verify(mealRepository, times(1)).findAll()
        assertFalse(meals.isEmpty())
        assertEquals(2, meals.size)
        assertEquals(MEAL_NAME_1, meals[0].name)
        assertEquals(MEAL_NAME_2, meals[1].name)
    }

    @Test
    fun givenNoMealEntities_whenCallingGetMeals_thenNothingIsReturned() {
        // given
        given(mealRepository.findAll()).willReturn(listOf())

        // when
        val meals = mealService.getMeals()

        // then
        verify(mealRepository, times(1)).findAll()
        assertTrue(meals.isEmpty())
    }

    @Test
    fun givenMealEntities_whenUpdateMealItemExists_thenUpdateMealItem() {
        // given
        val mealItem = defaultMealDBO1()
        given(mealRepository.findById(MEAL_ID_1)).willReturn(Optional.of(mealItem))
        given(mealRepository.save(any())).willReturn(mealItem)

        val newMealName = MEAL_NAME_1 + "a"
        val meal = Meal(MEAL_ID_1, newMealName, MEAL_PORTIONSIZE_1, MEAL_DIRECTIONS_1, emptyList(), MEAL_SOURCE_1, MEAL_IMAGE_URL_1)

        // when
        val updatedMeal = mealService.updateMeal(meal)

        // then
        assertEquals(updatedMeal, meal)
        assertEquals(mealItem.name, newMealName)
        verify(mealRepository, times(1)).save(mealItem)
    }

    @Test
    fun givenMeal_whenUpdateMealItemNotExists_thenDontUpdateAnything() {
        // given
        given(mealRepository.findById(MEAL_ID_1)).willReturn(Optional.empty())

        val meal = Meal(MEAL_ID_1, MEAL_NAME_1 + "a", MEAL_PORTIONSIZE_1, MEAL_DIRECTIONS_1, emptyList(), MEAL_SOURCE_1, MEAL_IMAGE_URL_1)

        // when
        val notUpdatedMeal = mealService.updateMeal(meal)

        // then
        assertEquals(notUpdatedMeal, meal)
        verify(mealRepository, times(0)).save(any())
    }
}
