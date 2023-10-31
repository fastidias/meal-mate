package com.meal.mate.controller

import java.time.LocalDateTime

data class ErrorResponse (
    val statusCode: Int,
    val statusMessage: String,
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
)
