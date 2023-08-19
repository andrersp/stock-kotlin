package com.example.stock.repository

import com.example.stock.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findFirstByEmail(email: String): User?
    fun findFirstByUserName(userName: String): User?
}