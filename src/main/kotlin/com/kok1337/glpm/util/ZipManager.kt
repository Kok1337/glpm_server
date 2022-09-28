package com.kok1337.glpm.util

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object ZipManager {
    fun zip(files: List<File>, zipFile: File) {
        ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile))).use { output ->
            files.forEach { file ->
                FileInputStream(file).use { input ->
                    BufferedInputStream(input).use { origin ->
                        val entry = ZipEntry(file.name)
                        output.putNextEntry(entry)
                        origin.copyTo(output, 1024)
                    }
                }
            }
        }
    }
}