package com.jeommechu.user.repository

import com.jeommechu.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    fun findByKakaoId(kakaoId: String): UserEntity?
}