package com.meal.mate

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meal.mate.repo.MealRepository
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
class MealIntegrationTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @SpyBean
    lateinit var mealRepository: MealRepository

    val mapper = jacksonObjectMapper()

    @AfterEach
    fun cleanUp() {
        mealRepository.deleteAll()
    }
}
