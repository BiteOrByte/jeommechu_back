package com.jeommechu.login.controller

import com.jeommechu.login.service.LoginService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(val loginService: LoginService) {

    /**
     * 카카오 콜백 API > 로그인/회원가입 진행 후 JWT 토큰 반환
     */
    @GetMapping("/callback")
    fun loginCallback(request: HttpServletRequest): ResponseEntity<String> {
        val code = request.getParameter("code")
        return ResponseEntity.ok(loginService.kakaoLogin(code))
    }
}