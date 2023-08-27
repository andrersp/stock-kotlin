package com.example.stock.domain.user

import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, unique = true)
    var userName: String = "",
    @Column(nullable = false, unique = true)
    var email: String = "",
    private val password: String = "",
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
) : UserDetails {
    override fun getAuthorities(): MutableSet<SimpleGrantedAuthority> =
        Collections.singleton(SimpleGrantedAuthority("user"))

    override fun getPassword() = this.password

    override fun getUsername() = this.userName

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}