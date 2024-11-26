package com.momokddi.login.controller

import com.momokddi.login.service.LoginService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(val loginService: LoginService) {

    @GetMapping("/callback")
    fun loginCallback(request: HttpServletRequest): ResponseEntity<String> {
        val code = request.getParameter("code")
        return ResponseEntity.ok(loginService.kakaoLogin(code))
    }
}