package com.meal.mate.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException

@ExtendWith(MockitoExtension::class)
class RestResponseEntityExceptionHandlerTest {
    @SpyBean
    private lateinit var restResponseEntityExceptionHandler: RestResponseEntityExceptionHandler

    @Test
    fun givenNotFoundException_whenCallingHandleNotFoundException_thenReturnNotFoundResponse() {
        // given
        //TODO: implement tests

        // when

        // then
    }
}
