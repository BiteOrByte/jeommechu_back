package com.momokddi.login.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.momokddi.common.JwtUtil
import com.momokddi.login.dto.TokenDto
import com.momokddi.login.dto.UserInfo
import com.momokddi.user.entity.UserEntity
import com.momokddi.user.repository.UserJpaRepository
import com.momokddi.user.service.NicknameGeneratorService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import java.sql.Timestamp


@Service
@Transactional
class LoginService(
    val userRepository: UserJpaRepository,
    val webClient: WebClient,
    val nicknameGeneratorService: NicknameGeneratorService,
    val jwtUtil: JwtUtil
) {
    @Value("\${kakao.client-id}")
    private lateinit var clientId: String

    @Value("\${kakao.redirect-url}")
    private lateinit var redirectUrl: String

    @Value("\${kakao.token-url}")
    private lateinit var tokenUrl: String

    @Value("\${kakao.user-info-url}")
    private lateinit var userInfoUrl: String

    fun kakaoLogin(code: String): String {
        // 인가 코드로 엑세스 토큰 요청
        val accessToken = this.getAccessToken(code)

        // 토큰으로 유저 정보 가져오기
        val userInfo = this.getUserInfo(accessToken)

        // 회원 가입 및 로그인 처리
        val response = this.userLogin(userInfo)

        return response
    }

    private fun getAccessToken(code: String): String {
        val body = LinkedMultiValueMap<String, String>()
        body.add("grant_type", "authorization_code");
        body.add("client_id",clientId );
        body.add("redirect_uri", redirectUrl);
        body.add("code", code);

        val response = webClient
                            .post()
                            .uri(tokenUrl)
                            .body(BodyInserters.fromFormData(body))
                            .retrieve()
                            .bodyToMono(String::class.java)
                            .block()

        val objectMapper = ObjectMapper().registerKotlinModule()
        var kakaoToken: TokenDto? = null

        try {
            kakaoToken = objectMapper.readValue(response, TokenDto::class.java)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }

        return kakaoToken!!.access_token
    }

    private fun getUserInfo(accessToken: String): UserInfo {
        val response = webClient
                        .post()
                        .uri(userInfoUrl)
                        .header("Authorization", "Bearer ${accessToken}")
                        .retrieve()
                        .bodyToMono(String::class.java)
                        .block()

        val objectMapper = ObjectMapper().registerKotlinModule()
        var userInfo: UserInfo? = null

        try {
            userInfo = objectMapper.readValue(response, UserInfo::class.java)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }

        return userInfo!!
    }

    private fun userLogin(userInfo: UserInfo): String {
        val user = userRepository.findByKakaoId(userInfo.id)

        if(user == null) {
            val userEntity = UserEntity(
                name = "특별한 먹방 요정", // nicknameGeneratorService.generateRandomNickname(),
                kakaoId = userInfo.id,
                createdAt = Timestamp(System.currentTimeMillis()),
                updatedAt = Timestamp(System.currentTimeMillis())
            )

            userRepository.save(userEntity)
        }

        return jwtUtil.generateToken(userInfo.id)
    }
}