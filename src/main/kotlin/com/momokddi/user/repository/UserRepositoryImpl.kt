package com.momokddi.user.repository

import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(private val userJpaRepository: UserJpaRepository) : UserRepository {

    override fun getRandomNickname(): String {
        return userJpaRepository.findRandomAdjectives() + " " + userJpaRepository.findRandomNouns()
    }

}