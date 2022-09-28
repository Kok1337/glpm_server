package com.kok1337.glpm.service

import java.io.File

interface FileService {
    fun getFile(fileName: String): File
    fun getZipFile(zipFileName: String, fileNameArray: Array<String>): File
}