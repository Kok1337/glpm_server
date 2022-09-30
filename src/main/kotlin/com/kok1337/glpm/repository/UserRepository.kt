package com.kok1337.glpm.repository

import com.kok1337.glpm.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByLogin(login: String): Optional<User>
    @Query(value = "select czl_check_pass as id from czl_check_pass(:login, :pass)", nativeQuery = true)
    fun getUserIdByLoginAndPass(@Param("login") login: String, @Param("pass") pass: String): Long
}