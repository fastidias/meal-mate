package com.meal.mate.controller

import com.meal.mate.MealIntegrationTest
import com.meal.mate.PATH_MEALS
import com.meal.mate.TestUtils
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.put

class MealControllerPUTMealIT : MealIntegrationTest() {
    @Test
    fun givenMealObject_whenCallRestUpdateMeal_thenUpdateAndReturnNoContent() {
        val mealJson = TestUtils.loadResource("/feuriges_nudelgericht.json")

        mockMvc.put("$PATH_MEALS/2f81508a-69e9-445f-ac82-40418c7bc42f") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(mealJson)
        }
            .andDo { print() }
            .andExpect {
                status { isNoContent() }
            }
    }

    @Test
    fun givenMealObjectOnWrongPathMealId_whenCallRestUpdateMeal_thenReturnUnprocessableEntity() {
        val mealJson = TestUtils.loadResource("/feuriges_nudelgericht.json")

        mockMvc.put("$PATH_MEALS/11111111-1111-1111-1111-111111111111") {
            contentType = MediaType.APPLICATION_JSON
            content = mealJson
        }
            .andDo { print() }
            .andExpect {
                status { isUnprocessableEntity() }
            }
    }
}
