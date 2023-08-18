package com.example.stock.controllers

import com.example.stock.dto.UserRequestDTO
import com.example.stock.entity.User
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*


@Tag(name = "User")
@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private lateinit var encode: BCryptPasswordEncoder

    @GetMapping
    fun getUser(): List<User> = listOf(
        User(userName = "andre", email = "rsp.assistencia@gmail.com", password = "admin123", id = 1L),
        User(userName = "Jessica", email = "jessicados@gmail.com", password = "admin1234", id = 2L)
    )

    @PostMapping
    fun createUser(@Valid @RequestBody payload: UserRequestDTO): UserRequestDTO {


        val password = encode.encode(payload.password.toString())
        println(password)
        return payload

    }

}