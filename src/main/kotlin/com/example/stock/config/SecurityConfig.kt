package com.example.stock.config

import com.example.stock.components.FilterToken
import com.example.stock.components.JwtToken
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val filterToken: FilterToken
) {

    private val jwtToken = JwtToken()


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
                    "/login*",
                ).permitAll()
                authorizeConfig.anyRequest().authenticated()
            }

            .addFilterBefore(this.filterToken, UsernamePasswordAuthenticationFilter::class.java)

            .build()


    }

    @Bean
    fun authManager(authenticationConfiguration: AuthenticationConfiguration) =
        authenticationConfiguration.authenticationManager

}