package com.kok1337.glpm.service

import org.springframework.web.multipart.MultipartFile
import java.io.File

interface FileService {
    fun getFile(fileName: String): File
    fun buildZipFile(zipFileName: String, fileNameArray: Array<String>)
    fun uploadFile(fileName: String, file: MultipartFile): Boolean
}