package com.jeommechu.common.exception

data class ErrorDetailsDto (
    val statusCode: Int,
    val message: String,
    val details: String
)
