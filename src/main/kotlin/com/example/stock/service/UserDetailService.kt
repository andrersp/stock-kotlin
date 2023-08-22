package com.example.stock.service

import com.example.stock.domain.user.UserRepository
import com.example.stock.exceptions.ApiException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserDetailService(
    private val repository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(userName: String): UserDetails {
        val user = repository.findFirstByUserName(userName) ?: throw ApiException(
            "INVALID_PAYLOAD",
            detail = "invalid username or password"
        )

        return UserSecurity(
            userName = user.userName,
            userPassword = user.password
        )

    }
}