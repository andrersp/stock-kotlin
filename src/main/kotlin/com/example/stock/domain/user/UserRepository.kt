package com.example.stock.domain.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findFirstByEmail(email: String): User?
    fun findFirstByUserName(userName: String): User?

    fun findByUserName(userName: String): User?

}