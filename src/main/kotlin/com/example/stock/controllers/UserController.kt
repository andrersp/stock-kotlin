package com.example.stock.controllers

import com.example.stock.security.TokenData
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "User")
@RestController
@RequestMapping("/user")
class UserController() {
    @GetMapping("/{userID}")
    fun getUserById(@PathVariable("userID") userId: Long): TokenData {

        return TokenData.getTokenData()
    }


}