package com.momokddi.user.service

import com.momokddi.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class NicknameGeneratorService(private val userRepository: UserRepository) {

    fun generateRandomNickname(): String {
        return userRepository.getRandomNickname()
    }
}
