package com.kok1337.glpm.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

object TokenManager {
    fun createToken(username: String, secret: String): String {
        val algorithm: Algorithm = Algorithm.HMAC256(secret)
        return JWT.create()
            .withIssuer(username)
            .sign(algorithm)
    }

    fun getUsername(token: String, secret: String): String {
        val algorithm: Algorithm = Algorithm.HMAC256(secret)
        val verifier = JWT.require(algorithm).build()
        val decodedJwt = verifier.verify(token)
        return decodedJwt.issuer
    }
}