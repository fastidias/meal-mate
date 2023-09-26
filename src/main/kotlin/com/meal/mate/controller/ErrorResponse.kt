package com.meal.mate.controller

import java.time.LocalDateTime

//TODO: when adding own exception classes, maybe move this class into that folder (or subfolder "handling")
data class ErrorResponse (
    val statusCode: Int,
    val statusMessage: String,
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
)
