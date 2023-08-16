package com.example.stock.controllers

import com.example.stock.entity.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
class UserController {

    @GetMapping
    fun getUser(): List<User> = listOf(
            User(userName = "andre", email = "rsp.assistencia@gmail.com", password = "admin123", id = 1L),
            User(userName = "Jessica", email = "jessicados@gmail.com", password = "admin1234", id = 2L)
    )

}