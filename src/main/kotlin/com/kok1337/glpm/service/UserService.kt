package com.kok1337.glpm.service

import com.kok1337.glpm.entity.User

interface UserService {
    fun getUserById(id: Long): User
    fun getUserByUsername(username: String): User
    fun getUserIdByLoginAndPassword(login: String, password: String): Long
}