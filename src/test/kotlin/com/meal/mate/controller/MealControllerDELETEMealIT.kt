package com.meal.mate.controller

import com.meal.mate.MealIntegrationTest
import com.meal.mate.TestUtils
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


class MealControllerDELETEMealIT : MealIntegrationTest() {

    @Test
    fun givenMealObject_whenCallRestDeleteMeal_thenReturnStatusNoContent() {
        val mealJson = TestUtils.loadResource("/feuriges_nudelgericht.json")

        mockMvc.post("/meals") {
            contentType = MediaType.APPLICATION_JSON
            content = mealJson
        } .andExpect { status { isCreated()} }

        val path = "/meals/2f81508a-69e9-445f-ac82-40418c7bc42f"
        mockMvc.delete(path).andDo { print() }
            .andExpect { status { isNoContent() } }

        mockMvc.get(path)
            .andExpect {status { isNotFound() }
        }
    }
}
