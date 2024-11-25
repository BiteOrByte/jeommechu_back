package com.jeommechu.common.exception

import org.springframework.http.HttpStatus

class CustomException(val httpStatus: HttpStatus, override val message: String, val details: String) : RuntimeException(message) {

}