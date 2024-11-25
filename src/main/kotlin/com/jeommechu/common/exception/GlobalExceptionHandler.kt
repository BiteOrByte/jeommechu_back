package com.jeommechu.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

@ControllerAdvice
class GlobalExceptionHandler {
    // 기본 예외 처리
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException, request: WebRequest): ResponseEntity<ErrorDetailsDto> {
        val errorDetailsDto = ErrorDetailsDto(e.httpStatus.value(), e.message, e.details)
        logger.error { e.message }
        return ResponseEntity(errorDetailsDto, e.httpStatus)
    }

    // 모든 예외 처리
    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<ErrorDetailsDto> {
        val errorDetailsDto = ErrorDetailsDto(
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = ex.message ?: "An error occurred",
            details = request.getDescription(false)
        )
        logger.error { ex.message }
        return ResponseEntity(errorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}