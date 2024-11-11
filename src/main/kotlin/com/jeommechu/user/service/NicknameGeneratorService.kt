package com.jeommechu.user.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jeommechu.user.dto.NicknameDto
import com.jeommechu.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.io.InputStream
import java.util.*

@Service
class NicknameGeneratorService(private val userRepository: UserRepository) {

    fun generateRandomNickname(): String {
        return userRepository.getRandomNickname()
    }
}
