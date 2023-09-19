package com.meal.mate.controller

import org.springframework.http.HttpStatus

//TODO: when adding own exception classes, maybe move this class into that folder (or subfolder "handling")
data class ErrorResponse (
    val statusCode: HttpStatus,
    val statusMessage: String,
    val message: String,
)
