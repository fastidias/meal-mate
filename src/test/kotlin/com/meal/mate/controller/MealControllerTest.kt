package com.meal.mate.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meal.mate.model.Ingredient
import com.meal.mate.model.Meal
import com.meal.mate.repo.MealItem
import com.meal.mate.repo.MealRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.put
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class MealControllerTest(@Autowired val mockMvc: MockMvc) {

    val mapper = jacksonObjectMapper()

    @Test
    fun givenStaticList_whenCallRestGet_thenReturnStaticJson() {
        mockMvc.get("/meals")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
    }

    @Test
    fun givenStaticElementOfStaticList_whenCallRestGetMeal_thenReturnStaticJson() {
        val uuid = "2f81508a-69e9-445f-ac82-40418c7bc42f"

        mockMvc.get("/meals/$uuid")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
    }

    @Test
    fun givenStaticElementOfStaticList_whenCallRestGetMeal_thenReturnNotFound() {
        val uuid = "11111111-1111-1111-1111-111111111111"

        mockMvc.get("/meals/$uuid")
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    fun givenMealObject_whenCallRestCreateMeal_thenReturnMealUrl() {
        val meal = Meal(
            UUID.fromString("2f81508a-69e9-445f-ac82-40418c7bc42f"),
            "Knoblauchspaghetti mit frischen Tomaten",
            4,
            listOf(Ingredient("Spaghetti","500","g"))
        )

        val mock = mockMvc.put("/meals") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(meal)
        }
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                header { string("Location", "/meals/2f81508a-69e9-445f-ac82-40418c7bc42f") }
            }.andReturn()

        mockMvc.get(mock.response.getHeaderValue("Location").toString())
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
    }
}