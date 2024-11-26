package com.momokddi.user.service

import com.momokddi.user.dto.UserResponseDto
import com.momokddi.user.repository.UserJpaRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserJpaRepository) {

    fun getUserName(kakaoId: Long): UserResponseDto {
        val user = userRepository.findByKakaoId(kakaoId)!!
        return UserResponseDto(name = user.name)
    }
}