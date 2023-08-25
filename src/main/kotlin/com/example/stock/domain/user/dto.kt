package com.example.stock.domain.user

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

data class UserResponseDTO(
    @Schema(example = "1")
    val id: Long,
    @Schema(example = "username")
    val userName: String,
    @Schema(example = "email@email.com")
    val email: String,
) {
    companion object MAPPER {
        fun fromEntity(entity: User): UserResponseDTO {
            return UserResponseDTO(
                id = entity.id,
                userName = entity.userName,
                email = entity.email
            )

        }

    }
}

data class LoginRequestDTO(
    @field:NotBlank(message = "required")
    @Schema(example = "username", required = true)
    val userName: String?,
    @field:NotBlank(message = "required")
    val password: String?
)

data class LoginResponseDTO(
    val tokenType: String,
    val accessToken: String
)