package com.kok1337.glpm.util

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.Callable


class ProcessReader(private val inputStream: InputStream) : Callable<Any> {
    override fun call(): Any {
        return BufferedReader(InputStreamReader(inputStream)).lines().toList()
    }
}