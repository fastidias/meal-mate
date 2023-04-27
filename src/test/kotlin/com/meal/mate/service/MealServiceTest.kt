package com.meal.mate.service

import com.meal.mate.repo.MealItem
import com.meal.mate.repo.MealRepository
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

private const val mealName = "testName1"

private const val mealId = "testId1"

@SpringBootTest
class MealServiceTest {
    @Autowired
    private lateinit var mealService: MealService
    @MockkBean
    private lateinit var mealRepository: MealRepository

    @Test
    fun givenStaticList_whenCallingGetMeals_thenStaticListIsReturned() {
        // given
        every { mealRepository.findAll() } returns listOf(MealItem(mealId, mealName))

        // when
        val meals = mealService.getMeals()

        // then
        verify(exactly = 1) { mealRepository.findAll() }
        assertFalse(meals.isEmpty())
        assertEquals(1, meals.size)
        assertEquals(mealName, meals[0].name)
    }
}