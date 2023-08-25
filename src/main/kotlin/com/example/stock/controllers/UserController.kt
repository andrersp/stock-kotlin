package com.example.stock.controllers

import com.example.stock.domain.user.User
import com.example.stock.domain.user.UserRequestDTO
import com.example.stock.domain.user.UserResponseDTO
import com.example.stock.domain.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@Tag(name = "User")
@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {


    @GetMapping
    fun getUser(): List<UserResponseDTO> = userService.getUsers()
        .map { user: User -> UserResponseDTO.fromEntity(user) }


    @PostMapping
    fun createUser(@Valid @RequestBody payload: UserRequestDTO) = userService.createUser(payload)


    @GetMapping("/{userID}")
    fun getUserById(@PathVariable("userID") userId: Long): UserResponseDTO {
        val user = userService.getUserById(userId)
        return UserResponseDTO.fromEntity(user)
    }


}