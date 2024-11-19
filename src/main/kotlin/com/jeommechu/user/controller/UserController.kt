package com.jeommechu.user.controller

import com.jeommechu.user.dto.UserResponseDto
import com.jeommechu.user.service.NicknameGeneratorService
import com.jeommechu.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController (private val nicknameGeneratorService: NicknameGeneratorService,
                      private val userService: UserService
                      ) {

    /**
     * 임시 닉네임 생성 api
     */
    @GetMapping("/api/nickname")
    fun getNickname(): String {
        return nicknameGeneratorService.generateRandomNickname()
    }

    @GetMapping
    fun getUser(@RequestParam(required = false) kakaoId: Long): ResponseEntity<UserResponseDto> {
        val name = userService.getUserName(kakaoId)
        return ResponseEntity(name, HttpStatus.OK)
    }
}