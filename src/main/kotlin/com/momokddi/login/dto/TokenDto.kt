package com.momokddi.login.dto

import java.io.Serializable

data class TokenDto(
    val token_type: String,
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val refresh_token_expires_in: Int
): Serializable