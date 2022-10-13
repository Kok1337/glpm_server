package com.kok1337.glpm.controller.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {
    inner class Message(val string: String)

    @GetMapping("/123")
    private fun downloadTermuxApkFile(): ResponseEntity<Message> {
        println("123")
        return ResponseEntity(Message("sdfgsdfgdfg"), HttpStatus.OK)
    }
}