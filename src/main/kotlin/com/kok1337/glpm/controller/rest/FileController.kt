package com.kok1337.glpm.controller.rest

import com.kok1337.glpm.service.FileService
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
import java.io.FileNotFoundException


@RestController
@RequestMapping("/file")
class FileController constructor(
    private val fileService: FileService,
) {
    @GetMapping("/termux")
    private fun downloadTermuxApkFile(): ResponseEntity<Resource> {
        val fileName = "termux_118_glpm.apk"
        println(fileName)
        return try {
            val file = fileService.getFile(fileName)
            createDownloadFileResponse(file)
        } catch (fileNotFoundException: FileNotFoundException) {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/termux-backup")
    private fun downloadTermuxBackupFile(): ResponseEntity<Resource> {
        val fileName = "termux-backup.tar.gz"
        println(fileName)
        return try {
            val file = fileService.getFile(fileName)
            createDownloadFileResponse(file)
        } catch (fileNotFoundException: FileNotFoundException) {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/glpm_local.backup")
    private fun downloadGlpmLocalBackupFile(): ResponseEntity<Resource> {
        val fileName = "glpm_local.backup"
        println(fileName)
        return try {
            val file = fileService.getFile(fileName)
            createDownloadFileResponse(file)
        } catch (fileNotFoundException: FileNotFoundException) {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/zip")
    private fun zip(): ResponseEntity<Resource> {
//        buildZipFile()
        val fileName = "region.zip"
        println(fileName)
        return try {
            val file = fileService.getFile(fileName)
            createDownloadFileResponse(file)
        } catch (fileNotFoundException: FileNotFoundException) {
            ResponseEntity.noContent().build()
        } catch (illegalStateException: IllegalStateException) {
            ResponseEntity.noContent().build()
        }
    }

    private fun buildZipFile() {
        val zipFileName = "region.zip"
        val fileNameArray = arrayOf("termux_118_glpm.apk", "termux-backup.tar.gz", "glpm_local.backup")
        fileService.buildZipFile(zipFileName, fileNameArray)
    }

    private fun createDownloadFileResponse(file: File): ResponseEntity<Resource> {
        val resource = InputStreamResource(FileInputStream(file))
        val httpHeader = HttpHeaders()
        httpHeader.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.name}")
        httpHeader.add("Cache-Control", "no-cache, no-store, must-revalidate")
        httpHeader.add("Pragma", "no-cache")
        httpHeader.add("Expires", "0")
        println("createDownloadFileResponse")
        return ResponseEntity.ok()
            .headers(httpHeader)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource)
    }
}