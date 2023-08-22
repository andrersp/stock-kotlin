package com.example.stock.controllers

import com.example.stock.components.JwtToken
import com.example.stock.domain.user.LoginRequestDTO
import com.example.stock.domain.user.LoginResponseDTO
import com.example.stock.domain.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "Login")
@RestController
@RequestMapping("/login")
class LoginController(private val userService: UserService, private val jwtUtil: JwtToken) {

    @PostMapping
    fun login(@Valid @RequestBody payload: LoginRequestDTO): LoginResponseDTO {
        val user = userService.login(userName = payload.userName.toString(), password = payload.password.toString())

        val token = jwtUtil.generateToken(userName = user.userName)

        val isValid = jwtUtil.isTokenValid(token)
        val userName = jwtUtil.getUserName(token)


        return LoginResponseDTO(
            tokenType = "Bearer",
            accessToken = token
        )


    }

}