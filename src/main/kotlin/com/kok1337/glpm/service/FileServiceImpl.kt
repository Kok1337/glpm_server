package com.kok1337.glpm.service

import com.kok1337.glpm.util.ZipManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

@Service
class FileServiceImpl constructor(
    @Value("\${file.source-folder}") private val sourceFolder: String,
) : FileService {
    override fun getFileResource(vararg fileNames: String): Resource {
        if (fileNames.size == 1) {
            val file = File("${sourceFolder}\\${fileNames[0]}")
            println("${sourceFolder}\\${fileNames[0]}    ${file.exists()}")
            if (!file.exists()) throwFileNotFoundException(file.name)
            return InputStreamResource(FileInputStream(file))
        }

        val files = fileNames.map { fileName -> File("${sourceFolder}\\${fileName}") }
        files.forEach { file -> if (!file.exists()) throwFileNotFoundException(file.name) }

        val zipFile = File("${sourceFolder}\\temp")
        ZipManager.zip(files, zipFile)
        return InputStreamResource(FileInputStream(zipFile))
    }

    private fun throwFileNotFoundException(fileName: String): Nothing =
        throw FileNotFoundException("Файл ${sourceFolder}\\${fileName} не существует!")
}