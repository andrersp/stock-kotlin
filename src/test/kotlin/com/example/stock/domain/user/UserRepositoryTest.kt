package com.example.stock.domain.user

import com.example.stock.common.UserConstraints.Users.USER
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.jdbc.Sql


@DataJpaTest
class UserRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository
) {
    @AfterEach
    fun afterEach() {
        USER.id = 0
    }

    @Test
    fun createUser_WithValidaData_ReturnUser() {
        val user = userRepository.save(USER)
        val sut = entityManager.find(USER.javaClass, user.id)
        assertThat(sut).isNotNull
        assertThat(sut.userName).isEqualTo(USER.userName)
    }

    @Test
    fun createUser_WithExistingUser_ThrowsException() {
        val userCreated = entityManager.persistAndFlush(USER)
        entityManager.detach(userCreated)
        userCreated.id = 0
        assertThatThrownBy { userRepository.save(userCreated) }.isInstanceOf(RuntimeException::class.java)
    }

    @Test
    fun getUser_ByExistingId_ReturnUser() {
        val user = entityManager.persistAndFlush(USER)
        val sut = userRepository.findById(user.id)
        assertThat(sut).isNotNull
        assertThat(sut.get().id).isEqualTo(user.id)
    }

    @Test
    fun getUser_ByUnexcitingId_ReturnEmpty() {
        val sut = userRepository.findById(1L)
        assertThat(sut).isEmpty
    }

    @Test
    fun getUser_ByExistingUserName_ReturnUser() {
        val user = entityManager.persistAndFlush(USER)
        val sut = userRepository.findByUserName(user.userName)
        assertThat(sut).isNotNull
        assertThat(sut?.userName).isEqualTo(user.userName)
    }

    @Test
    fun getUser_ByUnexcitingUserName_ReturnEmpty() {
        val sut = userRepository.findByUserName("userRandon")
        assertThat(sut).isNull()
    }

    @Test
    @Sql("/import_users.sql")
    fun listUsers_ReturnUsers() {
        val sut = userRepository.findAll()
        assertThat(sut.size).isEqualTo(3)
    }


}