package com.example.stock.components

import com.example.stock.service.UserDetailService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class FilterToken(
    private val jwtToken: JwtToken,
    private val userDetailService: UserDetailService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var token: String = ""
        val authorizationHeader: String? =
            request.getHeader("Authorization")
        if (authorizationHeader != null) {
            token = authorizationHeader.replace("Bearer", "")

            val userName = jwtToken.getUserName(token)
            val user = userDetailService.loadUserByUsername(userName)
            val auth = UsernamePasswordAuthenticationToken(user, null, user.authorities)
            SecurityContextHolder.getContext().authentication = auth


        }

        filterChain.doFilter(request, response)

    }


}