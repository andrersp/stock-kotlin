package com.example.stock.domain.user

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    var userName: String = "",
    var email: String = "",
    var password: String = "",
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
)