package com.example.stock.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class AppSecurityConfig(
) {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()


}