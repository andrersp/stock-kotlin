package com.example.stock.controllers

import com.example.stock.dto.UserRequestDTO
import com.example.stock.entity.User
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "User")
@RestController
@RequestMapping("/user")
class UserController {

    @GetMapping
    fun getUser(): List<User> = listOf(
            User(userName = "andre", email = "rsp.assistencia@gmail.com", password = "admin123", id = 1L),
            User(userName = "Jessica", email = "jessicados@gmail.com", password = "admin1234", id = 2L)
    )

    @PostMapping
    fun createUser(@Valid @RequestBody payload: UserRequestDTO): UserRequestDTO{
        return payload

    }

}