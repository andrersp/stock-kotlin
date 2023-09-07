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

    @Value("\${security.jwt-expire-hours}")
    private lateinit var expiredHours: String


    fun generateToken(): String = Jwts.builder()
        .setClaims(
            mutableMapOf(
                "canalVenda" to "1",
                "canalSolicitacao" to "valor2"
            )
        )
        .setExpiration(Date(System.currentTimeMillis() + hoursToMilliseconds()))
        .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()), SignatureAlgorithm.HS512)
        .compact()

    private fun getClaims(token: String) =
        Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.toByteArray())).build()
            .parseClaimsJws(token).body


    fun parseToken(token: String): TokenData {
        val claims = getClaims(token)
        val canalVenda = claims["canalVenda"].toString()
        val canalSolicitacao = claims["canalSolicitacao"].toString()

        return TokenData(canalVenda = canalVenda, canalSolicitacao = canalSolicitacao)
    }


    private fun hoursToMilliseconds(): Long = expiredHours.toLong() * 60 * 60 * 1000


}