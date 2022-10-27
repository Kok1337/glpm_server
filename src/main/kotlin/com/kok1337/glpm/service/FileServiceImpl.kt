package com.kok1337.glpm.service

import com.kok1337.glpm.util.ZipManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileNotFoundException

@Service
class FileServiceImpl constructor(
    @Value("\${app.file.source-folder}") private val sourceFolder: String,
    @Value("\${app.file.zip-password}") private val zipPassword: String,
) : FileService {
    override fun getFile(fileName: String): File {
        val pathname = "${sourceFolder}\\${fileName}"
        val file = File(pathname)
        if (!file.exists()) throw FileNotFoundException("Файл $pathname не существует!")
        return file
    }

    override fun buildZipFile(zipFileName: String, fileNameArray: Array<String>) {
        if (fileNameArray.isEmpty()) throw IllegalStateException("Отсутствуют файлы!")
        val files = fileNameArray.map { fileName -> File("${sourceFolder}\\${fileName}") }
        val incorrectFiles = files.filter { file -> !file.exists() }
        if (incorrectFiles.isNotEmpty()) throw FileNotFoundException("Файлы: ${incorrectFiles.joinToString { file -> file.absolutePath }} не существуют!")
        val zipFile = File("${sourceFolder}\\${zipFileName}")
        ZipManager.zip(files, zipFile, zipPassword)
    }
}