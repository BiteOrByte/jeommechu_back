package com.momokddi.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth.anyRequest().permitAll() // 모든 요청을 인증 없이 허용
            }
            .csrf { it.disable() } // CSRF 비활성화
            .cors { it.configurationSource(corsConfigurationSource()) } // CORS 비활성화

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = listOf("*")  // 모든 출처 패턴 허용
        configuration.allowedMethods = listOf("*") // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
        configuration.allowedHeaders = listOf("*") // 모든 헤더 허용
        configuration.allowCredentials = true // 자격 증명 허용 (Optional)

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration) // 모든 경로에 대해 CORS 설정 적용
        return source
    }
}