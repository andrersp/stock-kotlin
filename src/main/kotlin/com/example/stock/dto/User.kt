package com.example.stock.dto


import com.example.stock.domain.user.User
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
) {
    fun toDomain(): User {
        return User(
            userName = this.userName.toString(),
            email = this.email.toString(),
            password = this.email.toString()
        )
    }
}

data class UserResponseDTO(
    @Schema(example = "1")
    val id: Long,
    @Schema(example = "username")
    val userName: String,
    @Schema(example = "email@email.com")
    val email: String,
)