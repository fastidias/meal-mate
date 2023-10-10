package com.meal.mate.controller

import com.meal.mate.MealIntegrationTest
import com.meal.mate.PATH_MEALS
import com.meal.mate.TestUtils.Companion.defaultMealDBO1
import com.meal.mate.TestUtils.Companion.defaultMealDBO2
import com.meal.mate.TestUtils.Companion.defaultMealDBOList
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.hamcrest.text.IsEmptyString.emptyString
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get

class MealControllerGETMealIT : MealIntegrationTest() {
    @Test
    fun givenDefaultMealItem_whenCallRestGetMeal_thenReturnMealJson() {
        // given
        val mealItem = defaultMealDBO1()
        mealRepository.save(mealItem)
        mealRepository.save(defaultMealDBO2())

        // when
        mockMvc.get("$PATH_MEALS/${mealItem.id}")
            .andDo { print() }
            .andExpect {

                // then
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(
                        """
                        {"id":"4d259eda-8318-463c-9d5f-ed1cd74b2e24","directions":"Sauerkraut","name":"Knoblauchspaghetti mit frischen Tomaten","portionSize":1,"ingredients":[{"name":"Spaghetti","quantity":"500","unit":"g"}],"imageSource":"https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"}
                        """
                    )
                }
            }
    }

    @Test
    fun givenDefaultMealItem_whenCallRestGetMeal_thenReturnNotFound() {
        // given
        mealRepository.saveAll(defaultMealDBOList())
        val uuid = "11111111-1111-1111-1111-111111111111"

        // when
        mockMvc.get("$PATH_MEALS/$uuid")
            .andDo { print() }

            //then
            .andExpect {
                status { isNotFound() }
                content {
                    jsonPath("$.statusMessage", IsEqual("Not Found"))
                    jsonPath("$.message", IsEqual("No Meal with Id 11111111-1111-1111-1111-111111111111 found."))
                    jsonPath("$.timestamp", IsNot(emptyString()))
                }
            }
    }
}
