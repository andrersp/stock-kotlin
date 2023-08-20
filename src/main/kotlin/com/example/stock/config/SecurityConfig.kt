package com.example.stock.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {


        return http
            .csrf { csrf -> csrf.disable() }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authorizeConfig ->
                authorizeConfig.requestMatchers(
                    "/login*",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/v2/api-docs/**",
                    "/swagger-resources/**",
                    "/user"
                ).permitAll()
                authorizeConfig.anyRequest().authenticated()
            }
//            .addFilterBefore(this.filterToken, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}