package com.kok1337.glpm.service

import org.springframework.core.io.Resource

interface FileService {
    fun getFileResource(vararg fileNames: String): Resource
}