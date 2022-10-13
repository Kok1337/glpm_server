package com.kok1337.glpm.service

import java.io.File

interface FileService {
    fun getFile(fileName: String): File
    fun buildZipFile(zipFileName: String, fileNameArray: Array<String>)
}