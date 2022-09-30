package com.kok1337.glpm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class GlpmApplication

fun main(args: Array<String>) {
    runApplication<GlpmApplication>(*args)
}
