package com.meal.mate.controller

import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.NullNode
import com.meal.mate.MealIntegrationTest
import com.meal.mate.PATH_MEALS
import com.meal.mate.TestUtils
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

class MealControllerPOSTMealIT : MealIntegrationTest() {
    @Test
    fun givenMealObject_whenCallRestCreateMeal_thenReturnMealUrl() {
        val mealJson = TestUtils.loadResource("/feuriges_nudelgericht_data.json")

        val mock = mockMvc.post(PATH_MEALS) {
            contentType = MediaType.APPLICATION_JSON
            content = mealJson
        }.andDo { print() }
            .andExpect {
                status { isCreated() }
                header { string("Location", Matchers.matchesPattern("/meals/[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}")) }
            }.andReturn()

        val expectedResponse = mapper.createObjectNode()
        expectedResponse.putIfAbsent("data", mealJson)

        mockMvc.get(mock.response.getHeaderValue("Location").toString())
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    // verify that we saved the correct values
                    json(
                        mapper.writeValueAsString(expectedResponse)
                    )
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
    }

    @Test
    fun givenMealObjectWithEmptyName_whenCallRestCreateMeal_thenReturnUnprocessableEntity() {
        val mealJson = TestUtils.loadResource("/feuriges_nudelgericht_data.json")
        mealJson.put("name", "")

        mockMvc.post(PATH_MEALS) {
            contentType = MediaType.APPLICATION_JSON
            content = mealJson
        }
            .andDo { print() }
            .andExpect {status { isUnprocessableEntity() } }
    }

    @Test
    fun givenMealObjectWithEmptyDirections_whenCallRestCreateMeal_thenReturnUnprocessableEntity() {
        val mealJson = TestUtils.loadResource("/feuriges_nudelgericht_data.json")
        mealJson.put("directions", "")

        mockMvc.post(PATH_MEALS) {
            contentType = MediaType.APPLICATION_JSON
            content = mealJson
        }
            .andDo { print() }
            .andExpect {status { isUnprocessableEntity() } }
    }

    @Test
    fun givenMealObjectWithEmptyIngredients_whenCallRestCreateMeal_thenReturnUnprocessableEntity() {
        val mealJson = TestUtils.loadResource("/feuriges_nudelgericht_data.json")
        mealJson.set<ArrayNode>("ingredients", mapper.createArrayNode())

        mockMvc.post(PATH_MEALS) {
            contentType = MediaType.APPLICATION_JSON
            content = mealJson
        }
            .andDo { print() }
            .andExpect {status { isUnprocessableEntity() } }
    }

    @Test
    fun givenMealObjectWithoutName_whenCallRestCreateMeal_thenReturnUnprocessableEntity() {
        val mealJson = TestUtils.loadResource("/feuriges_nudelgericht_data.json")
        mealJson.set<NullNode>("name", NullNode.getInstance())

        mockMvc.post(PATH_MEALS) {
            contentType = MediaType.APPLICATION_JSON
            content = mealJson
        }
            .andDo { print() }
            .andExpect {status { isBadRequest() } }
    }

    @Test
    fun givenMealObjectWithoutDirections_whenCallRestCreateMeal_thenReturnUnprocessableEntity() {
        val mealJson = TestUtils.loadResource("/feuriges_nudelgericht_data.json")
        mealJson.set<NullNode>("directions", NullNode.getInstance())

        mockMvc.post(PATH_MEALS) {
            contentType = MediaType.APPLICATION_JSON
            content = mealJson
        }
            .andDo { print() }
            .andExpect {status { isBadRequest() } }
    }

    @Test
    fun givenMealObjectWithoutIngredients_whenCallRestCreateMeal_thenReturnUnprocessableEntity() {
        val mealJson = TestUtils.loadResource("/feuriges_nudelgericht_data.json")
        mealJson.set<NullNode>("ingredients", NullNode.getInstance())

        mockMvc.post(PATH_MEALS) {
            contentType = MediaType.APPLICATION_JSON
            content = mealJson
        }
            .andDo { print() }
            .andExpect {status { isBadRequest() } }
    }
}
