package com.kok1337.glpm.config

import com.kok1337.glpm.interceptor.RestInterceptor
import com.kok1337.glpm.service.AuthorizationService
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
open class RestInterceptorConfig constructor(
    private val authorizationService: AuthorizationService
): WebMvcConfigurerAdapter() {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(RestInterceptor(authorizationService))
    }
}