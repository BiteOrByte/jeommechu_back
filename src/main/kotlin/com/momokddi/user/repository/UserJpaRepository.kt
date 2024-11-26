package com.momokddi.user.repository

import com.momokddi.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByKakaoId(kakaoId: Long): UserEntity?

    @Query(nativeQuery = true, value = """
    SELECT nouns
    FROM nicknames ORDER BY RAND() LIMIT 1
    """)
    fun findRandomNouns(): String

    @Query(nativeQuery = true, value = """
    SELECT adjectives
    FROM nicknames ORDER BY RAND() LIMIT 1
    """)
    fun findRandomAdjectives(): String
}