package com.meal.mate.service

import com.meal.mate.repo.MealRepository
import com.ninjasquad.springmockk.SpykBean
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ExtendWith(MockKExtension::class)
class MealServiceTest {
    @Autowired
    private lateinit var mealService: MealService
    @SpykBean
    private lateinit var mealRepository: MealRepository

    @Test
    fun givenStaticList_whenCallingGetMeals_thenStaticListIsReturned() {
        // given

        // when
        val meals = mealService.getMeals()

        // then
        assertTrue(meals.isEmpty())
        verify(exactly = 1) { mealRepository.findAll() }
    }
}