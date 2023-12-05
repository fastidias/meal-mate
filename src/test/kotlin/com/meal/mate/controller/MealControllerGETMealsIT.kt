package com.meal.mate.controller

import com.meal.mate.MealIntegrationTest
import com.meal.mate.PATH_MEALS
import com.meal.mate.TestUtils.Companion.defaultMealDBOList
import org.junit.jupiter.api.Test
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
                        [{"data":{"name":"Knoblauchspaghetti mit frischen Tomaten","portionSize":1,"directions":"Sauerkraut","ingredients":[{"name":"Spaghetti","quantity":"500","unit":"g"}],"source":"https://www.chefkoch.de/rezepte/168021072969366/Feuriges-Nudelgericht.html?zufall=on","imageSource":"https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"},"meta":{"id":"4d259eda-8318-463c-9d5f-ed1cd74b2e24"}},{"data":{"name":"Vegane Kicher-Spätzle","portionSize":1,"directions":"Die Zutaten für den Teig zusammenrühren und kurz ruhen lassen. Dann über eine Spätzle-Reibe in kochendes Wasser schaben.","ingredients":[{"name":"Kichererbsen","quantity":"40","unit":"g"}],"source":"https://www.chefkoch.de/rezepte/168021072969366/Kichererbsen.html?zufall=on","imageSource":"https://www.chefkoch.de/rezepte/3461541515680773/Vegane-Kicher-Spaetzle.html"},"meta":{"id":"4d259eda-8318-463c-9d5f-ed1cd74b2e25"}}]
                        """
                    )
                }
            }

        // then
        verify(mealRepository, times(1)).findAll()
    }
}
