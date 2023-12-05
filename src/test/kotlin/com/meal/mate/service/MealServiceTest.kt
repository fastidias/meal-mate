package com.meal.mate.service

import com.meal.mate.MEAL_DIRECTIONS_1
import com.meal.mate.MEAL_ID_1
import com.meal.mate.MEAL_IMAGE_URL_1
import com.meal.mate.MEAL_NAME_1
import com.meal.mate.MEAL_NAME_2
import com.meal.mate.MEAL_PORTIONSIZE_1
import com.meal.mate.MEAL_SOURCE_1
import com.meal.mate.TestUtils.Companion.defaultMealDBO1
import com.meal.mate.TestUtils.Companion.defaultMealDBOList
import com.meal.mate.model.Meal
import com.meal.mate.model.MealData
import com.meal.mate.model.MealMeta
import com.meal.mate.repo.MealDBO
import com.meal.mate.repo.MealRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.never
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
        assertEquals(MEAL_NAME_1, meals[0].data.name)
        assertEquals(MEAL_NAME_2, meals[1].data.name)
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
        val meal = Meal(MealData(newMealName, MEAL_PORTIONSIZE_1, MEAL_DIRECTIONS_1, emptyList(), MEAL_SOURCE_1, MEAL_IMAGE_URL_1), MealMeta(MEAL_ID_1))

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

        val meal = Meal(MealData(MEAL_NAME_1 + "a", MEAL_PORTIONSIZE_1, MEAL_DIRECTIONS_1, emptyList(), MEAL_SOURCE_1, MEAL_IMAGE_URL_1), MealMeta(MEAL_ID_1))

        // when
        val notUpdatedMeal = mealService.updateMeal(meal)

        // then
        assertEquals(notUpdatedMeal, meal)
        verify(mealRepository, times(0)).save(any())
    }

    @Test
    fun givenId_whenCallDelete_thenDeleteInRepository() {
        // given
        val id = "a7a08be2-cb67-44d3-82bd-b6c55839c6b9"

        // when
        val mealDbo = MealDBO(id, "name",1,"directions",emptyList(),"source","imageSource")
        given(mealRepository.findById(any())).willReturn(Optional.of(mealDbo))
        mealService.deleteMeal(id)

        // then
        verify(mealRepository, times(1)).findById(id)
        verify(mealRepository, times(1)).delete(mealDbo)
    }

    @Test
    fun givenNonExistingId_whenCallDelete_thenDontDeleteAnything() {
        // given
        val id = "a7a08be2-cb67-44d3-82bd-b6c55839c6b9"

        // when
        given(mealRepository.findById(any())).willReturn(Optional.empty())
        mealService.deleteMeal(id)

        // then
        verify(mealRepository, times(1)).findById(id)
        verify(mealRepository, never()).delete(any())
    }
}
