package com.example.stock.common

import com.example.stock.domain.user.User

class UserConstraints {


    companion object Users {
        val USER = User(userName = "testUser", email = "testeemail.com", password = "teste1234")
    }
}