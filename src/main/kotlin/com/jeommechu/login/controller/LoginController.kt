package com.jeommechu.login.controller

import com.jeommechu.login.dto.LoginResponseDto
import com.jeommechu.login.service.LoginService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(val loginService: LoginService) {

    @GetMapping("/callback")
    fun loginCallback(request: HttpServletRequest): ResponseEntity<LoginResponseDto> {
        val code = request.getParameter("code")
        val accessToken = loginService.getAccessToken(code)

        return loginService.loginToService(accessToken)
    }
}