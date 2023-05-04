package com.meal.mate.service

import com.meal.mate.repo.MealItem
import com.meal.mate.repo.MealRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

private const val MEAL_NAME = "testName1"

private val mealId = UUID.fromString("4d259eda-8318-463c-9d5f-ed1cd74b2e24")

@SpringBootTest
class MealServiceTest {
    @Autowired
    private lateinit var mealService: MealService
    @MockkBean
    private lateinit var mealRepository: MealRepository

    @Test
    fun givenStaticList_whenCallingGetMeals_thenStaticListIsReturned() {
        // given
        every { mealRepository.findAll() } returns listOf(MealItem(mealId, MEAL_NAME))

        // when
        val meals = mealService.getMeals()

        // then
        verify(exactly = 1) { mealRepository.findAll() }
        assertFalse(meals.isEmpty())
        assertEquals(1, meals.size)
        assertEquals(MEAL_NAME, meals[0].name)
    }
}