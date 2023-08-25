package com.example.stock.controllers


import com.example.stock.domain.user.LoginRequestDTO
import com.example.stock.domain.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "Login")
@RestController
@RequestMapping("/login")
class LoginController(private val userService: UserService) {

    @PostMapping
    fun login(@Valid @RequestBody payload: LoginRequestDTO) =
        ResponseEntity.ok(userService.login(payload))

}