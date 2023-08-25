package com.example.stock.domain.user


import com.example.stock.exceptions.ApiException
import com.example.stock.security.JwtUtil
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(
    private val repository: UserRepository,
    private val encoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager

) {
    fun createUser(payload: UserRequestDTO): UserResponseDTO {
        val userByEmail = repository.findFirstByEmail(payload.email.toString())

        if (userByEmail != null) {
            throw ApiException("RESOURCE_EXISTS", detail = "email exist")
        }

        val userByUserName = repository.findFirstByUserName(payload.userName.toString())

        if (userByUserName != null) {
            throw ApiException("RESOURCE_EXISTS", detail = "username exist")
        }


        val user = User(
            userName = payload.userName.toString(),
            email = payload.email.toString(),
            password = encoder.encode(payload.password.toString())
        )

        try {
            repository.save(user)
        } catch (exc: DataIntegrityViolationException) {
            throw ApiException("INTERNAL_ERROR")
        }

        return UserResponseDTO.fromEntity(user)
    }

    fun getUsers(): List<User> = repository.findAll()

    fun getUserById(userID: Long): User =
        repository.findById(userID).orElseThrow { ApiException("RESOURCE_NOT_FOUND") }

    fun login(payload: LoginRequestDTO): LoginResponseDTO {

        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    payload.userName.toString(),
                    payload.password.toString()
                )
            )
        } catch (exc: BadCredentialsException) {

            throw ApiException(errorCode = "INVALID_PAYLOAD", detail = "invalid username or password")
        }


        val user = repository.findByUserName(payload.userName.toString())

        return LoginResponseDTO(
            tokenType = "Bearer",
            accessToken = jwtUtil.generateToken(user!!.userName)
        )
    }

}