package com.jeommechu.login.dto

data class LoginResponseDto(
    var loginSuccess: Boolean?,
    val userInfo: UserInfo
)