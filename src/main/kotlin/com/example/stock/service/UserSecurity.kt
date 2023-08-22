package com.example.stock.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserSecurity(
    val userName: String,
    val userPassword: String,
) : UserDetails {
    override fun getAuthorities(): MutableSet<SimpleGrantedAuthority> =
        Collections.singleton(SimpleGrantedAuthority("user"))

    override fun getPassword() = userPassword

    override fun getUsername() = userName

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}