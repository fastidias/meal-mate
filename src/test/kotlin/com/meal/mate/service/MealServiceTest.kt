package com.meal.mate.service

import com.meal.mate.model.Meal
import com.meal.mate.repo.MealItem
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

private const val MEAL_NAME = "testName1"
private val mealId = UUID.fromString("4d259eda-8318-463c-9d5f-ed1cd74b2e24")

@ExtendWith(MockitoExtension::class)
class MealServiceTest {
    @InjectMocks
    private lateinit var mealService: MealService
    @Mock
    private lateinit var mealRepository: MealRepository

    @Test
    fun givenStaticList_whenCallingGetMeals_thenStaticListIsReturned() {
        // given
        given(mealRepository.findAll()).willReturn(listOf(MealItem(mealId, MEAL_NAME)))

        // when
        val meals = mealService.getMeals()

        // then
        verify(mealRepository, times(1)).findAll()
        assertFalse(meals.isEmpty())
        assertEquals(1, meals.size)
        assertEquals(MEAL_NAME, meals[0].name)
    }

    @Test
    fun givenMeal_whenUpdateMealItemExists_thenUpdateMealItem(){
        // given
        val mealItem = MealItem(mealId, MEAL_NAME)
        given(mealRepository.findById(mealId)).willReturn(mealItem)
        given(mealRepository.save(any())).willReturn(mealItem)

        val newMealName = MEAL_NAME + "a"
        val meal = Meal(mealId , newMealName, 0, emptyList())

        // when
        val updatedMeal = mealService.updateMeal(meal)

        // then
        assertEquals(updatedMeal, meal)
        assertEquals(mealItem.name, newMealName)
        verify(mealRepository, times(1)).save(mealItem)
    }

    @Test
    fun givenMeal_whenUpdateMealItemNotExists_thenDontUpdateAnything(){
        // given
        given(mealRepository.findById(mealId)).willReturn(null)

        val meal = Meal(mealId , MEAL_NAME + "a", 0, emptyList())

        // when
        val notUpdatedMeal = mealService.updateMeal(meal)

        // then
        assertEquals(notUpdatedMeal, meal)
        verify(mealRepository, times(0)).save(any())
    }
}