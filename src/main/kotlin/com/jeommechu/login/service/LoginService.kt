package com.jeommechu.login.service

import com.jeommechu.login.domain.KakaoLogin
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class LoginService(
    val kakaoLogin: KakaoLogin
) {
    fun kakaoLogin(code: String): String {
        // 인가 코드로 엑세스 토큰 요청
        val accessToken = kakaoLogin.getAccessToken(code)

        // 토큰으로 유저 정보 가져오기
        val userInfo = kakaoLogin.getUserInfo(accessToken)

        // 회원 가입 및 로그인 처리
        val response = kakaoLogin.userLogin(userInfo)

        return response
    }
}