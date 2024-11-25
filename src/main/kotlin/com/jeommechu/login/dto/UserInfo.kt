package com.jeommechu.login.dto

/**
 * 카카오에서 받아오는 유저 정보 형식
 */
data class UserInfo(
    val id: Long,
    val connected_at: String
)