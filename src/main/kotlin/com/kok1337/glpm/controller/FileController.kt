package com.kok1337.glpm.controller

import com.kok1337.glpm.service.FileService
import com.kok1337.glpm.util.ZipManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.FileInputStream


@RestController
@RequestMapping("/file")
class FileController constructor(
    private val fileService: FileService,
    @Value("\${file.source-folder}") private val sourceFolder: String,
) {

    @GetMapping("/termux")
    private fun downloadTermuxApkFile(): ResponseEntity<Resource> {
        val fileName = "termux_118_glpm.apk"
        val file = File("${sourceFolder}\\${fileName}")
        if (!file.exists()) return ResponseEntity.noContent().build()
        return createDownloadFileResponse(file)
    }

    @GetMapping("/termux-backup")
    private fun downloadTermuxBackupFile(): ResponseEntity<Resource> {
        val fileName = "termux-backup"
        val file = File("${sourceFolder}\\${fileName}")
        if (!file.exists()) return ResponseEntity.noContent().build()
        return createDownloadFileResponse(file)
    }

    @GetMapping("/glpm_local.backup")
    private fun downloadGlpmLocalBackupFile(): ResponseEntity<Resource> {
        val fileName = "glpm_local.backup"
        val file = File("${sourceFolder}\\${fileName}")
        if (!file.exists()) return ResponseEntity.noContent().build()
        return createDownloadFileResponse(file)
    }

    @GetMapping("/zip")
    private fun zip(): ResponseEntity<Resource> {
        val zipFileName = "region.zip"
        val fileNameArray = arrayOf("termux_118_glpm.apk", "termux-backup", "glpm_local.backup")
        val files = fileNameArray.map { fileName -> File("${sourceFolder}\\${fileName}") }
        files.forEach { file -> if (!file.exists()) return ResponseEntity.noContent().build() }
        val zipFile = File("${sourceFolder}\\${zipFileName}")
        ZipManager.zip(files, zipFile)
        return createDownloadFileResponse(zipFile)
    }

    private fun createDownloadFileResponse(file: File): ResponseEntity<Resource> {
        val resource = InputStreamResource(FileInputStream(file))
        val httpHeader = HttpHeaders()
        httpHeader.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.name}")
        httpHeader.add("Cache-Control", "no-cache, no-store, must-revalidate")
        httpHeader.add("Pragma", "no-cache")
        httpHeader.add("Expires", "0")
        return ResponseEntity.ok()
            .headers(httpHeader)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource)
    }


    /*    private fun createDownloadFileResponse(resource: Resource, fileName: String): ResponseEntity<Resource> {
            val httpHeader = HttpHeaders()
            httpHeader.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${fileName}")
            httpHeader.add("Cache-Control", "no-cache, no-store, must-revalidate")
            httpHeader.add("Pragma", "no-cache")
            httpHeader.add("Expires", "0")
            println(resource.file.length())
            return ResponseEntity.ok()
                .headers(httpHeader)
                .contentLength(resource.file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource)
        }*/

    @GetMapping("")
    private fun index(): String {
        return "index"
    }
}