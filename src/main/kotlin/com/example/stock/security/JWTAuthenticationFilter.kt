package com.example.stock.security

import com.example.stock.exceptions.ApiError
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JWTAuthenticationFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    private val Expired = "EXPIRED"
    private val Signature = "SIGNATURE"
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val log = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java.name)

        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return

        }

        val lengthBearerWithSpace = 7
        val jwtToken = authHeader.substring(lengthBearerWithSpace)

        try {
            jwtUtil.isTokenValid(jwtToken)
        } catch (_: ExpiredJwtException) {
            val err = getError(Expired)
            response.status = HttpStatus.FORBIDDEN.value()
            response.writer.write(ObjectMapper().writeValueAsString(err))
            return
        } catch (_: SignatureException) {
            val err = getError(Signature)
            response.status = HttpStatus.FORBIDDEN.value()
            response.writer.write(ObjectMapper().writeValueAsString(err))
            return
        } catch (_: Exception) {
            val err = getError(Signature)
            response.status = HttpStatus.FORBIDDEN.value()
            response.writer.write(ObjectMapper().writeValueAsString(err))
            return
        }

        val userName = jwtUtil.getUserName(jwtToken)

        log.info("jwt: $jwtToken\nusername: $userName")

        if (SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(userName)
            val authenticationToken = UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.authorities
            )

            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authenticationToken

        }
        filterChain.doFilter(request, response)
    }


    private fun getError(type: String) = when (type) {
        Expired -> ApiError(errorCode = "EXPIRED_TOKEN", errorType = "Authentication", detail = "Token expired")
        Signature -> ApiError(errorCode = "INVALID_TOKEN", errorType = "Authentication", detail = "Token invalid")
        else -> ApiError(errorCode = "INVALID_TOKEN", errorType = "Authentication", detail = "Token invalid")
    }

}