package com.kok1337.glpm.interceptor

import com.kok1337.glpm.service.AuthorizationService
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RestInterceptor(
    private val authorizationService: AuthorizationService,
) : HandlerInterceptor {

    companion object {
        private val UNAUTHORIZED_CODE = HttpStatus.UNAUTHORIZED.value()
        private const val LOGIN_PATH = "/login"
        private const val TOKEN_HEADER = "token"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return true
//        val path = request.servletPath
//        if (path == LOGIN_PATH) return true
//
//        val token = request.getHeader(TOKEN_HEADER)
//        println("RestInterceptor $token")
//        val isAuthorized = authorizationService.userIsAuthorized(token)
//        if (isAuthorized) return true
//
//        response.writer.write("Token is not valid")
//        response.status = UNAUTHORIZED_CODE
//        return false
    }
}