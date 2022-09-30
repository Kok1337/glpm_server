package com.kok1337.glpm.service

interface AuthorizationService {
    fun userIsAuthorized(token: String?): Boolean
}