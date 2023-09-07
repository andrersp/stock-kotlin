package com.example.stock.security

import com.example.stock.exceptions.ApiError
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JWTAuthenticationFilter(
    private val jwtUtil: JwtUtil,
) : OncePerRequestFilter() {

    object SECURITY {
        const val EXPIRED = "EXPIRED"
        const val SIGNATURE = "SIGNATURE"
        const val MISSING = "MISSING"
    }


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            val err = getError(SECURITY.MISSING)
            response.status = HttpStatus.FORBIDDEN.value()
            response.writer.write(ObjectMapper().writeValueAsString(err))
            return

        }

        val lengthBearerWithSpace = 7
        val jwtToken = authHeader.substring(lengthBearerWithSpace)

        var tokenData = TokenData()

        try {
            tokenData = jwtUtil.parseToken(jwtToken)
        } catch (_: ExpiredJwtException) {
            val err = getError(SECURITY.EXPIRED)
            response.status = HttpStatus.FORBIDDEN.value()
            response.writer.write(ObjectMapper().writeValueAsString(err))
            return
        } catch (_: SignatureException) {
            val err = getError(SECURITY.SIGNATURE)
            response.status = HttpStatus.FORBIDDEN.value()
            response.writer.write(ObjectMapper().writeValueAsString(err))
            return
        } catch (_: Exception) {
            val err = getError(SECURITY.MISSING)
            response.status = HttpStatus.FORBIDDEN.value()
            response.writer.write(ObjectMapper().writeValueAsString(err))
            return
        }
        if (SecurityContextHolder.getContext().authentication == null) {
            val authenticationToken = UsernamePasswordAuthenticationToken(
                tokenData,
                null,
                setOf()
            )
            SecurityContextHolder.getContext().authentication = authenticationToken
        }
        filterChain.doFilter(request, response)
    }


    private fun getError(type: String) = when (type) {
        SECURITY.EXPIRED -> ApiError(
            errorCode = "EXPIRED_TOKEN",
            errorType = "Authentication",
            detail = "Token expired"
        )

        SECURITY.SIGNATURE -> ApiError(
            errorCode = "INVALID_TOKEN",
            errorType = "Authentication",
            detail = "Token invalid"
        )

        SECURITY.MISSING -> {
            ApiError(errorCode = "MISSING_TOKEN", errorType = "Authentication", detail = "Missing token")
        }

        else -> ApiError(errorCode = "MISSING_TOKEN", errorType = "Authentication", detail = "Missing token")
    }

}