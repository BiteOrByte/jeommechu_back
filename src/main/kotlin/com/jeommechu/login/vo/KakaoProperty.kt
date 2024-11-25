package com.jeommechu.login.vo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "kakao")
data class KakaoProperty(
    val clientId: String,
    val redirectUrl: String,
    val tokenUrl: String,
    val userInfoUrl: String
)
