package com.example.stock.controllers

import com.example.stock.domain.user.User
import com.example.stock.domain.user.UserService
import com.example.stock.dto.UserRequestDTO
import com.example.stock.dto.UserResponseDTO
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@Tag(name = "User")
@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {


    @GetMapping
    fun getUser(): List<UserResponseDTO> = userService.getUsers()
        .map { user: User -> UserResponseDTO(userName = user.userName, email = user.email, id = user.id) }

    @PostMapping
    fun createUser(@Valid @RequestBody payload: UserRequestDTO): UserResponseDTO {

        val user = userService.createUser(payload.toDomain())

        return UserResponseDTO(userName = user.userName, email = user.email, id = user.id)

    }


    @GetMapping("/{userID}")
    fun getUserById(@PathVariable("userID") userId: Long): UserResponseDTO {
        val user = userService.getUserById(userId)
        return UserResponseDTO(
            userName = user.userName,
            email = user.email,
            id = user.id
        )
    }


}