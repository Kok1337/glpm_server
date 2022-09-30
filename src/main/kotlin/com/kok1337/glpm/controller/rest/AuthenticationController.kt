package com.kok1337.glpm.controller.rest

import com.kok1337.glpm.dto.AuthenticationRequestDTO
import com.kok1337.glpm.dto.AuthenticationResponseDTO
import com.kok1337.glpm.service.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception

@RestController
@RequestMapping
class AuthenticationController constructor(
    private val authenticationService: AuthenticationService,
) {
    @PostMapping("/login")
    private fun login(@RequestBody authenticationRequestDTO: AuthenticationRequestDTO): ResponseEntity<AuthenticationResponseDTO> {
        return try {
            ResponseEntity(authenticationService.login(authenticationRequestDTO), HttpStatus.OK)
        } catch (exception: Exception) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }
}