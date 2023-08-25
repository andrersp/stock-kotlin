package com.example.stock.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtUtil {
    @Value("\${security.jwt-secret}")
    private lateinit var secretKey: String


    fun generateToken(userName: String): String = Jwts.builder()
        .setSubject(userName)
        .setExpiration(Date(System.currentTimeMillis() + 600000))
        .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()), SignatureAlgorithm.HS512)
        .compact()

    private fun getClaims(token: String) =
        Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.toByteArray())).build()
            .parseClaimsJws(token).body

    fun isTokenValid(token: String): Boolean {
        val claims = getClaims(token)
        val expirationDate = claims.expiration
        val now = Date(System.currentTimeMillis())
        return now.before(expirationDate)
    }

    fun getUserName(token: String): String = getClaims(token).subject


}