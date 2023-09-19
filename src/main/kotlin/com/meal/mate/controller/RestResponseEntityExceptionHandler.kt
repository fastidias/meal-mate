package com.meal.mate.controller

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest


@ControllerAdvice
//TODO: when adding own exception classes, maybe move this class into that folder (or subfolder "handling")
class RestResponseEntityExceptionHandler {

    //TODO: write own non-static NotFoundException::class with a message-attribute
    @ExceptionHandler(value = [NotFoundException::class])
    internal fun handleNotFoundException(exception: NotFoundException, request: WebRequest? ): ResponseEntity<Any> {
        return handleExceptionInternal(HttpStatus.NOT_FOUND, exception.message)
    }

    @ExceptionHandler(value = [Exception::class])
    internal fun handleUndefinedException( exception: Exception?, request: WebRequest? ): ResponseEntity<Any> {
        val bodyOfResponse = "An unknown exception occurred"
        return handleExceptionInternal(HttpStatus.INTERNAL_SERVER_ERROR, bodyOfResponse)
    }

    @ExceptionHandler(value = [Error::class])
    internal fun handleUndefinedError( error: Error?, request: WebRequest? ): ResponseEntity<Any> {
        val bodyOfResponse = "An unknown error occurred"
        return handleExceptionInternal(HttpStatus.INTERNAL_SERVER_ERROR, bodyOfResponse)
    }

    internal fun handleExceptionInternal(httpStatus: HttpStatus, bodyOfResponse: String?): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(httpStatus, httpStatus.reasonPhrase, bodyOfResponse ?: "")
        return ResponseEntity.status(httpStatus).body(errorResponse)
    }
}
