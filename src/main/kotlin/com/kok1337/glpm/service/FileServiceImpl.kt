package com.kok1337.glpm.service

import com.kok1337.glpm.util.ZipManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileNotFoundException

@Service
class FileServiceImpl constructor(
    @Value("\${app.file.source-folder}") private val sourceFolderPath: String,
    @Value("\${app.file.zip-password}") private val zipPassword: String,
    @Value("\${app.file.upload-folder}") private val uploadFolderPath: String,
    @Value("\${app.file.download-folder}") private val downloadFolderPath: String,
) : FileService {
    override fun getFile(fileName: String): File {
        val pathname = "${sourceFolderPath}\\${fileName}"
        val file = File(pathname)
        if (!file.exists()) throw FileNotFoundException("Файл $pathname не существует!")
        return file
    }

    override fun buildZipFile(zipFileName: String, fileNameArray: Array<String>) {
        if (fileNameArray.isEmpty()) throw IllegalStateException("Отсутствуют файлы!")
        val files = fileNameArray.map { fileName -> File("${sourceFolderPath}\\${fileName}") }
        val incorrectFiles = files.filter { file -> !file.exists() }
        if (incorrectFiles.isNotEmpty()) throw FileNotFoundException("Файлы: ${incorrectFiles.joinToString { file -> file.absolutePath }} не существуют!")
        val zipFile = File("${sourceFolderPath}\\${zipFileName}")
        ZipManager.zip(files, zipFile, zipPassword)
    }

    override fun uploadFile(fileName: String, file: MultipartFile): Boolean {
        val uploadFolder = File(uploadFolderPath)
        if (!uploadFolder.exists() && !uploadFolder.mkdirs()) return false
        if (file.isEmpty) return false
        val uploadFile = File(uploadFolder, fileName)
        file.transferTo(uploadFile)
        return true
    }

    override fun getBackup(): File {
        val folder = File(downloadFolderPath)
        if (!folder.exists() || !folder.isDirectory) throw IllegalStateException("Папка не существует!")
        val filename = folder.list()?.first { filename -> filename.contains(".backup") }
            ?: throw IllegalStateException("Backup не существует!")
        val backup = File(folder, filename)
        if (!backup.isFile || backup.length() == 0L) throw IllegalStateException("Backup не существует!")
        return backup
    }
}