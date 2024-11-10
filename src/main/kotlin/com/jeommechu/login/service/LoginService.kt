package com.jeommechu.login.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.jeommechu.login.dto.KakaoAccountDto
import com.jeommechu.login.dto.LoginResponseDto
import com.jeommechu.login.dto.TokenDto
import com.jeommechu.login.dto.UserInfo
import com.jeommechu.user.entity.UserEntity
import com.jeommechu.user.repository.UserJpaRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.sql.Timestamp


@Service
class LoginService(val userJpaRepository: UserJpaRepository) {
    @Value("\${kakao.client.id}")
    private lateinit var clientId: String

    @Value("\${kakao.redirect.url}")
    private lateinit var redirectUrl: String

    @Value("\${kakao.token.url}")
    private lateinit var tokenUrl: String

    @Value("\${kakao.user.info.url}")
    private lateinit var userInfoUrl: String

    fun getAccessToken(code: String): TokenDto? {
        val headers = HttpHeaders()
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        // Http Response Body 객체 생성
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", clientId)
        params.add("redirect_uri", redirectUrl)
        params.add("code", code)

        // 헤더와 바디 합치기 위해 Http Entity 객체 생성
        val tokenRequest = HttpEntity(params, headers)

        // 카카오로부터 Access token 받아오기
        val restTemplate = RestTemplate()
        val accessTokenResponse: ResponseEntity<String> = restTemplate.exchange(
            tokenUrl,
            HttpMethod.POST,
            tokenRequest,
            String::class.java
        )

        println(accessTokenResponse.toString())

        // JSON Parsing
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        var tokenDto: TokenDto? = null
        try {
            tokenDto = objectMapper.readValue(accessTokenResponse.body, TokenDto::class.java)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }

        return tokenDto
    }

    fun loginToService(accessToken: TokenDto?): ResponseEntity<LoginResponseDto> {
        val userInfo = getUserInfo(accessToken)
        val loginResponseDto = LoginResponseDto(false, userInfo)

        val existOwner: UserEntity? = userJpaRepository.findByKakaoId(userInfo.id)
        try {
            if (existOwner == null) {
                println("처음 로그인 하는 회원입니다.")
                userJpaRepository.save(
                    UserEntity(
                        name = userInfo.nickName,
                        kakaoId = userInfo.id,
                        createdAt = Timestamp(System.currentTimeMillis()),
                        updatedAt = Timestamp(System.currentTimeMillis())
                    )
                )
            }
            loginResponseDto.loginSuccess = true

            return ResponseEntity(loginResponseDto, HttpStatus.OK)
        } catch (e: Exception) {
            loginResponseDto.loginSuccess = false
            return ResponseEntity(loginResponseDto, HttpStatus.BAD_REQUEST)
        }
    }

    fun getUserInfo(accessToken: TokenDto?): UserInfo {
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $accessToken")
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        val accountInfoRequest = HttpEntity<MultiValueMap<String, String>>(headers)

        // POST 방식으로 API 서버에 요청 후 response 받아옴
        val accountInfoResponse: ResponseEntity<String> = restTemplate.exchange(
            userInfoUrl,
            HttpMethod.POST,
            accountInfoRequest,
            String::class.java
        )

        // JSON Parsing (-> kakaoAccountDto)
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        var kakaoAccountDto: KakaoAccountDto? = null
        try {
            kakaoAccountDto = objectMapper.readValue(accountInfoResponse.body, KakaoAccountDto::class.java)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }

        return UserInfo(
            id = kakaoAccountDto!!.id,
            nickName = kakaoAccountDto.kakao_account.profile["nickname"].toString()
        )
    }
}