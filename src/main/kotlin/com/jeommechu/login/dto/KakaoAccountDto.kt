package com.jeommechu.login.dto

data class KakaoAccountDto(
    val id: Long,
    val connected_at: String,
    val kakao_account: kakaoAccount
)

data class kakaoAccount(
    val profile_nickname_meeds_agreement: Boolean,
    val profile: Map<String, Any>
)