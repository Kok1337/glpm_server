package com.kok1337.glpm.service

import com.kok1337.glpm.dto.AuthenticationRequestDTO
import com.kok1337.glpm.dto.AuthenticationResponseDTO
import com.kok1337.glpm.util.TokenManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl constructor(
    @Value("\${app.jwt.secret}") private val secret: String,
    private val userService: UserService,
): AuthenticationService {
    override fun login(authenticationRequestDTO: AuthenticationRequestDTO): AuthenticationResponseDTO {
        val login = authenticationRequestDTO.username
        val password = authenticationRequestDTO.password
        val userId = userService.getUserIdByLoginAndPassword(login, password)
        val user = userService.getUserById(userId)
        val token = TokenManager.createToken(user.login!!, secret)
        return AuthenticationResponseDTO(token)
    }
}