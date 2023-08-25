package com.example.stock.security

import com.example.stock.domain.user.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class AppSecurityConfig(
    private val userRepository: UserRepository
) {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun userDetailService() = UserDetailsService { userRepository.findByUserName(it) }

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager =
        configuration.authenticationManager

    @Bean
    fun autheticationProvider() = DaoAuthenticationProvider().apply {
        setUserDetailsService(userDetailService())
        setPasswordEncoder(passwordEncoder())
    }
}