package com.example.stock.entity

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