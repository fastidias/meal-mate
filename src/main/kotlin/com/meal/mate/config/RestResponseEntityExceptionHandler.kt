package com.meal.mate.config

import com.meal.mate.controller.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest


@ControllerAdvice
class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = [NoSuchElementException::class])
    internal fun handleNotFoundException(exception: NoSuchElementException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(HttpStatus.NOT_FOUND, exception.message)
    }

    @ExceptionHandler(value = [Exception::class])
    internal fun handleUndefinedException(exception: Exception, request: WebRequest): ResponseEntity<Any> {
        val bodyOfResponse = "An unknown exception occurred"
        return handleExceptionInternal(HttpStatus.INTERNAL_SERVER_ERROR, bodyOfResponse)
    }

    internal fun handleExceptionInternal(httpStatus: HttpStatus, bodyOfResponse: String?): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(httpStatus.value(), httpStatus.reasonPhrase, bodyOfResponse ?: "")
        return ResponseEntity.status(httpStatus).body(errorResponse)
    }
}
