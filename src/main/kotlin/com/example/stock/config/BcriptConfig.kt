package com.example.stock.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class BcriptConfig {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
    
}