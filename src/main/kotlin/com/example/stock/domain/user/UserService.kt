package com.example.stock.domain.user

import com.example.stock.dto.UserRequestDTO
import com.example.stock.dto.UserResponseDTO
import com.example.stock.exceptions.ApiException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(private val repository: UserRepository, private val encoder: PasswordEncoder) {
    fun createUser(user: User): User {
        val userByEmail = repository.findFirstByEmail(user.email.toString())

        if (userByEmail != null) {
            throw ApiException("RESOURCE_EXISTS", detail = "email exist")
        }

        val userByUserName = repository.findFirstByUserName(user.userName.toString())

        if (userByUserName != null) {
            throw ApiException("RESOURCE_EXISTS", detail = "username exist")
        }

        try {
            return repository.save(user)
        } catch (exc: DataIntegrityViolationException) {
            throw ApiException("INTERNAL_ERROR")
        }
    }

    fun getUsers(): List<User> = repository.findAll()

    fun getUserById(userID: Long): User =
        repository.findById(userID).orElseThrow { ApiException("RESOURCE_NOT_FOUND") }


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