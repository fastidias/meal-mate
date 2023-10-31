package com.meal.mate.controller

import com.fasterxml.jackson.databind.node.NullNode
import com.meal.mate.MealIntegrationTest
import com.meal.mate.PATH_MEALS
import com.meal.mate.TestUtils
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

class MealControllerPOSTMealIT : MealIntegrationTest() {
    @Test
    fun givenMealObject_whenCallRestCreateMeal_thenReturnMealUrl() {
        val mealJson = TestUtils.loadResource("/feuriges_nudelgericht.json")

        val mock = mockMvc.post(PATH_MEALS) {
            contentType = MediaType.APPLICATION_JSON
            content = mealJson
        }.andDo { print() }
            .andExpect {
                status { isCreated() }
                header { string("Location", "/meals/2f81508a-69e9-445f-ac82-40418c7bc42f") }
            }.andReturn()

        mockMvc.get(mock.response.getHeaderValue("Location").toString())
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    // verify that we saved the correct values
                    json(
                        mapper.writeValueAsString(mealJson)
                    )
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
    }
    @Test
    fun givenMealObjectWithoutId_whenCallRestCreateMeal_thenReturnUnprocessableEntity() {
        val mealJson = TestUtils.loadResource("/feuriges_nudelgericht.json")
        mealJson.put("id", NullNode.getInstance())

        mockMvc.post(PATH_MEALS) {
            contentType = MediaType.APPLICATION_JSON
            content = mealJson
        }.andExpect {status { isUnprocessableEntity() } }

        mockMvc.get("/meals/2f81508a-69e9-445f-ac82-40418c7bc42f")
            .andExpect { status { isNotFound() }}
    }
}
