package com.jeommechu.login.domain

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.jeommechu.common.JwtUtil
import com.jeommechu.login.dto.TokenDto
import com.jeommechu.login.dto.UserInfo
import com.jeommechu.login.vo.KakaoProperty
import com.jeommechu.user.entity.UserEntity
import com.jeommechu.user.repository.UserJpaRepository
import com.jeommechu.user.service.NicknameGeneratorService
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import java.sql.Timestamp

// TODO: 도메인도 서비스 어노테이션을 사용하는지 확인 필요
@Service
class KakaoLogin(
    val userRepository: UserJpaRepository,
    val webClient: WebClient,
    val nicknameGeneratorService: NicknameGeneratorService,
    val jwtUtil: JwtUtil,
    val kakaoProperty: KakaoProperty
) {
    /**
     * 카카오 access 토큰 발급
     */
    fun getAccessToken(code: String): String {
        val body = LinkedMultiValueMap<String, String>()
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoProperty.clientId);
        body.add("redirect_uri", kakaoProperty.redirectUrl);
        body.add("code", code);

        val response = webClient
            .post()
            .uri(kakaoProperty.tokenUrl)
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

    /**
     * 카카오에서 유저 정보 가져오기
     */
    fun getUserInfo(accessToken: String): UserInfo {
        val response = webClient
            .post()
            .uri(kakaoProperty.userInfoUrl)
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

    /**
     * 카카오에서 받아온 정보로 회원 가입 및 JWT 토큰 발급
     */
    fun userLogin(userInfo: UserInfo): String {
        val user = userRepository.findByKakaoId(userInfo.id)

        if(user == null) {
            val userEntity = UserEntity(
                name = nicknameGeneratorService.generateRandomNickname(),
                kakaoId = userInfo.id,
                createdAt = Timestamp(System.currentTimeMillis()),
                updatedAt = Timestamp(System.currentTimeMillis())
            )

            userRepository.save(userEntity)
        }

        return jwtUtil.generateToken(userInfo.id)
    }
}