package com.example.stock.service

import com.example.stock.dto.UserRequestDTO
import com.example.stock.dto.UserResponseDTO
import com.example.stock.entity.User
import com.example.stock.exceptions.ApiException
import com.example.stock.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val encoder: PasswordEncoder, private val repository: UserRepository) {

    fun createUser(user: UserRequestDTO): UserResponseDTO {

        val userByEmail = repository.findFirstByEmail(user.email.toString())

        if (userByEmail != null) {
            throw ApiException("RESOURCE_EXISTS", detail = "email exist")
        }

        val userByUserName = repository.findFirstByUserName(user.userName.toString())

        if (userByUserName != null) {
            throw ApiException("RESOURCE_EXISTS", detail = "username exist")
        }

        val userEntity = userDtoToEntity(user)
        try {
            repository.save(userEntity)
        } catch (exc: DataIntegrityViolationException) {
            throw ApiException("INTERNAL_ERROR")
        }
        return userEntityToUserDTO(userEntity)
    }


    private fun userDtoToEntity(userDTO: UserRequestDTO): User =
        User(
            userName = userDTO.userName.toString(),
            email = userDTO.email.toString(),
            password = encoder.encode(userDTO.password.toString())
        )

    private fun userEntityToUserDTO(userEntity: User) = UserResponseDTO(
        id = userEntity.id.toLong(),
        userName = userEntity.userName.toString(),
        email = userEntity.email.toString()
    )


}