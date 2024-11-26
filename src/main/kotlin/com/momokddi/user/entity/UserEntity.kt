package com.momokddi.user.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "users")
data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long? = null,

    val name: String,

    val kakaoId: Long,

    val createdAt: Timestamp,

    val updatedAt: Timestamp
)

