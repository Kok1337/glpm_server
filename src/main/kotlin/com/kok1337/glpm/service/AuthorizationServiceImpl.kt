package com.kok1337.glpm.service

import com.kok1337.glpm.util.TokenManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AuthorizationServiceImpl constructor(
    @Value("\${app.jwt.secret}") private val secret: String,
    private val userService: UserService,
) : AuthorizationService {
    override fun userIsAuthorized(token: String?): Boolean {
        if (token == null) return false
        return try {
            val username = TokenManager.getUsername(token, secret)
            println("AuthorizationServiceImpl $username")
            userService.getUserByUsername(username)
            true
        } catch (exception: Exception) {
            false
        }
    }
}