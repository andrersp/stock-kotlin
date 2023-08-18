package com.example.stock.dto


import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserRequestDTO(
    @field:NotBlank(message = "required")
    @Schema(example = "username", required = true)
    val userName: String?,
    @field:NotBlank(message = "required")
    @field:Email(message = "invalid")
    val email: String?,
    @field:NotBlank(message = "required")
    val password: String?
)