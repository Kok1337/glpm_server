package com.kok1337.glpm.service

import com.kok1337.glpm.util.ZipManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader


@Service
class FileServiceImpl constructor(
    @Value("\${app.file.source-folder}") private val sourceFolderPath: String,
    @Value("\${app.file.zip-password}") private val zipPassword: String,
    @Value("\${app.file.upload-folder}") private val uploadFolderPath: String,
    @Value("\${app.file.download-folder}") private val downloadFolderPath: String,
    @Value("\${app.script.load-changes}") private val loadChangesScript: String,
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
        val userId = 2
        val outputFileName = "change_out$userId.backup"
        val command =
            "powershell.exe \"$loadChangesScript $userId '${uploadFolder.absolutePath}\\' '${uploadFile.name}' '$downloadFolderPath\\' '$outputFileName'\""
        println(command)

        val cmd = "\"C:\\Users\\bas\\AppData\\Local\\Programs\\pgAdmin 4\\v6\\runtime\\pg_dump.exe\" --file \"C:\\Users\\bas\\Desktop\\server_files\\out\\123.backup\" --lock-wait-timeout 2 --no-sync --host \"172.16.24.199\" --port \"5432\" --format=p --schema=\"glpm\" --username \"postgres\" --no-password   --verbose --blobs \"glpm_for_plan\""

        val arrCom = listOf(
            "powershell.exe",
            "-noexit",
            "\"$loadChangesScript $userId '${uploadFolder.absolutePath}\\' '${uploadFile.name}' '$downloadFolderPath\\' '$outputFileName'\""
        )
//        val arrCom = listOf(cmd)
        val processBuilder = ProcessBuilder()
        processBuilder.command(arrCom)

        try {
            val process = processBuilder.start()
            BufferedReader(InputStreamReader(process.inputStream)).use { buf ->
                var line = buf.readLine()
                while (line != null) {
                    println(line)
                    line = buf.readLine()
                }
            }

            BufferedReader(InputStreamReader(process.errorStream)).use { buf ->
                var line = buf.readLine()
                while (line != null) {
                    println(line)
                    line = buf.readLine()
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

//        Runtime.getRuntime().exec(cmd)

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

//            val task = ProcessReader(process.inputStream)
//            val future: Future<List<String>> = pool.submit(task) as Future<List<String>>
//            val results = future.get()
//            results.forEach {
//                println(it)
//            }

//            val exitCode = process.waitFor()
//
//            if (exitCode != 0) {
//                val platformMessages = """
//                ${String(BufferedInputStream(process.inputStream).readAllBytes(), Charset.defaultCharset())}
//                ${String(BufferedInputStream(process.errorStream).readAllBytes(), Charset.defaultCharset())}
//            """.trimIndent().trim()
//                println(platformMessages)
//            }