package com.kok1337.glpm.service

import com.kok1337.glpm.dto.AuthenticationRequestDTO
import com.kok1337.glpm.dto.AuthenticationResponseDTO

interface AuthenticationService {
    fun login(authenticationRequestDTO: AuthenticationRequestDTO): AuthenticationResponseDTO
}