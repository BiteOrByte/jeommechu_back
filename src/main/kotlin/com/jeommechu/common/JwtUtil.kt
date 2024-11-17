package com.jeommechu.common

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.HS256
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtUtil {
    @Value("\${jwt.secret-key}")
    private lateinit var secretKey: String
    private val expirationTime = 60 * 60 * 1000L // 1시간

    fun generateToken(id: Long): String {
        return Jwts.builder()
            .setSubject(id.toString())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()), HS256)
            .compact()
    }

    fun extractId(token: String): String? {
        return getClaims(token).subject
    }

    fun validateToken(token: String): Boolean {
        return try {
            getClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secretKey.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body
    }
}