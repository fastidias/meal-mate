package com.meal.mate.controller

import com.meal.mate.MealIntegrationTest
import com.meal.mate.PATH_MEALS
import com.meal.mate.TestUtils.Companion.defaultMealDBOList
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.hamcrest.text.IsEmptyString.emptyString
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get

class MealControllerGETMealsIT : MealIntegrationTest() {
    @Test
    fun givenEmptyDatabase_whenCallRestGet_thenReturnEmptyBody() {
        // when
        mockMvc.get(PATH_MEALS)
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json("[]")
                }
            }

        // then
        verify(mealRepository, times(1)).findAll()
    }

    @Test
    fun givenDefaultMealItem_whenCallRestGetMeals_thenReturnMealListJson() {
        // given
        mealRepository.saveAll(defaultMealDBOList())

        // when
        mockMvc.get(PATH_MEALS)
            .andExpect {

                // then
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(
                        """
                        [{"id":"4d259eda-8318-463c-9d5f-ed1cd74b2e24","directions":"Sauerkraut","name":"Knoblauchspaghetti mit frischen Tomaten","portionSize":1,"ingredients":[{"name":"Spaghetti","quantity":"500","unit":"g"}],"imageSource":"https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"},{"id":"4d259eda-8318-463c-9d5f-ed1cd74b2e25","directions":"Die Zutaten für den Teig zusammenrühren und kurz ruhen lassen. Dann über eine Spätzle-Reibe in kochendes Wasser schaben.","name":"Vegane Kicher-Spätzle","portionSize":1,"ingredients":[{"name":"Kichererbsen","quantity":"40","unit":"g"}],"imageSource":"https://www.chefkoch.de/rezepte/3461541515680773/Vegane-Kicher-Spaetzle.html"}]
                        """
                    )
                }
            }

        // then
        verify(mealRepository, times(1)).findAll()
    }

    @Test
    fun givenDefaultMealDBOs_whenThrowRuntimeException_thenReturnInternalServerError() {
        // given
        given(mealRepository.findAll()).willThrow(RuntimeException("my message"))

        // when
        mockMvc.get(PATH_MEALS)
            .andExpect {
                // then
                status { isInternalServerError() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.statusCode", IsEqual(500))
                    jsonPath("$.statusMessage", IsEqual("Internal Server Error"))
                    jsonPath("$.message", IsEqual("An unknown exception occurred"))
                    jsonPath("$.timestamp", IsNot(emptyString()))
                }
            }

        // then
        verify(mealRepository, times(1)).findAll()
    }

    @Test
    fun givenDefaultMealDBOs_whenThrowError_thenReturnInternalServerError() {
        // given
        given(mealRepository.findAll()).willThrow(Error("my message"))

        // when
        mockMvc.get(PATH_MEALS)
            .andExpect {
                // then
                status { isInternalServerError() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        jsonPath("$.statusCode", IsEqual(500))
                        jsonPath("$.statusMessage", IsEqual("Internal Server Error"))
                        jsonPath("$.message", IsEqual("An unknown exception occurred"))
                        jsonPath("$.timestamp", IsNot(emptyString()))
                    }
                }
            }

        // then
        verify(mealRepository, times(1)).findAll()
    }
}
