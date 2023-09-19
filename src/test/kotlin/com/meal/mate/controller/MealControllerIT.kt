package com.meal.mate.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meal.mate.MealTestBase
import com.meal.mate.PATH_MEALS
import com.meal.mate.model.Ingredient
import com.meal.mate.model.Meal
import com.meal.mate.repo.MealRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*


@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
class MealControllerIT : MealTestBase() {
    @Autowired
    lateinit var mockMvc: MockMvc

    @SpyBean
    lateinit var mealRepository: MealRepository

    private val mapper = jacksonObjectMapper()

    @AfterEach
    fun cleanUp() {
        mealRepository.deleteAll()
    }

    @Test
    fun givenEmptyDatabase_whenCallRestGet_thenReturnEmptyBody() {
        // when
        mockMvc.get(PATH_MEALS)
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }

        // then
        verify(mealRepository, times(1)).findAll()
    }

    @Test
    fun givenDefaultMealItem_whenCallRestGetMeals_thenReturnMealListJson() {
        // given
        mealRepository.saveAll(defaultMealItemList())

        // when
        mockMvc.get(PATH_MEALS)
            .andExpect {

                // then
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(
                        """
                        [{"id":"4d259eda-8318-463c-9d5f-ed1cd74b2e24","directions":"Sauerkraut","name":"Knoblauchspaghetti mit frischen Tomaten","portionSize":1,"ingredients":[{"name":"Spaghetti","quantity":"500","unit":"g"}],"imagesource":"https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"},{"id":"4d259eda-8318-463c-9d5f-ed1cd74b2e25","directions":"Die Zutaten für den Teig zusammenrühren und kurz ruhen lassen. Dann über eine Spätzle-Reibe in kochendes Wasser schaben.","name":"Vegane Kicher-Spätzle","portionSize":1,"ingredients":[{"name":"Kichererbsen","quantity":"40","unit":"g"}],"imagesource":"https://www.chefkoch.de/rezepte/3461541515680773/Vegane-Kicher-Spaetzle.html"}]
                        """
                    )
                }
            }

        // then
        verify(mealRepository, times(1)).findAll()
    }

    @Test
    fun givenDefaultMealItem_whenCallRestGetMeal_thenReturnMealJson() {
        // given
        val mealItem = defaultMealItem1()
        mealRepository.save(mealItem)
        mealRepository.save(defaultMealItem2())

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
                        {"id":"4d259eda-8318-463c-9d5f-ed1cd74b2e24","directions":"Sauerkraut","name":"Knoblauchspaghetti mit frischen Tomaten","portionSize":1,"ingredients":[{"name":"Spaghetti","quantity":"500","unit":"g"}],"imagesource":"https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"}
                        """
                    )
                }
            }
    }

    @Test
    fun givenDefaultMealItem_whenCallRestGetMeal_thenReturnNotFound() {
        // given
        mealRepository.saveAll(defaultMealItemList())
        val uuid = "11111111-1111-1111-1111-111111111111"

        // when
        mockMvc.get("$PATH_MEALS/$uuid")
            .andDo { print() }

            //then
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    fun givenMealObject_whenCallRestCreateMeal_thenReturnMealUrl() {
        val meal = Meal(
            "2f81508a-69e9-445f-ac82-40418c7bc42f",
            "Knoblauchspaghetti mit frischen Tomaten",
            4,
            "Sauerkraut",
            listOf(Ingredient("Spaghetti", "500", "g")),
            "https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"
        )

        val mock = mockMvc.post(PATH_MEALS) {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(meal)
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
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
    }

    @Test
    fun givenMealObject_whenCallRestCreateMeal_thenReturnMealPortionSize() {
        val meal = Meal(
            "2f81508a-69e9-445f-ac82-40418c7bc42f",
            "Knoblauchspaghetti mit frischen Tomaten",
            4,
            "Sauerkraut",
            listOf(Ingredient("Spaghetti", "500", "g")),
            "https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"
        )

        mockMvc.post(PATH_MEALS) {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(meal)
        }.andExpect {
            status { isCreated() }
        }.andReturn()

        val createdMealDBO = mealRepository.findById(meal.id)
        assertTrue(createdMealDBO.isPresent)
        val createdMeal = createdMealDBO.get()

        assertEquals(meal.portionSize, createdMeal.portionSize)
    }

    @Test
    fun givenMealObject_whenCallRestUpdateMeal_thenUpdateAndReturnNoContent() {
        val mealToUpdate = Meal(
            "2f81508a-69e9-445f-ac82-40418c7bc42f",
            "Knoblauchspaghetti mit alten Tomaten",
            4,
            "Sauerkraut",
            listOf(Ingredient("Spaghetti", "500", "g")),
            "https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"
        )

        mockMvc.put("$PATH_MEALS/${mealToUpdate.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(mealToUpdate)
        }
            .andDo { print() }
            .andExpect {
                status { isNoContent() }
            }
    }

    @Test
    fun givenMealObjectOnWrongPathMealId_whenCallRestUpdateMeal_thenReturnUnprocessableEntity() {
        val mealToUpdate = Meal(
            "2f81508a-69e9-445f-ac82-40418c7bc42f",
            "Knoblauchspaghetti mit alten Tomaten",
            4,
            "Sauerkraut",
            listOf(Ingredient("Spaghetti", "500", "g")),
            "https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"
        )

        mockMvc.put("$PATH_MEALS/11111111-1111-1111-1111-111111111111") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(mealToUpdate)
        }
            .andDo { print() }
            .andExpect {
                status { isUnprocessableEntity() }
            }
    }

    @Test
    fun givenMealObject_whenCallRestDeleteMeal_thenReturnStatusNoContent() {
        val meal = Meal(
            "2f81508a-69e9-445f-ac82-40418c7bc42f",
            "Knoblauchspaghetti mit frischen Tomaten",
            4,
            "Sauerkraut",
            listOf(Ingredient("Spaghetti", "500", "g")),
            "https://www.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-760x400.webp"
        )

        mockMvc.put("/meals") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(meal)
        }

        mockMvc.delete("/meals/2f81508a-69e9-445f-ac82-40418c7bc42f").andDo { print() }
            .andExpect { status { isNoContent() } }
    }
}