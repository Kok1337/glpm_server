package com.kok1337.glpm.util

import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.model.enums.CompressionLevel
import net.lingala.zip4j.model.enums.EncryptionMethod
import java.io.File

object ZipManager {
    fun zip(files: List<File>, zipFile: File, password: String) {
        val zipParameters = ZipParameters()
        zipParameters.isEncryptFiles = true
        zipParameters.compressionLevel = CompressionLevel.NORMAL
        zipParameters.encryptionMethod = EncryptionMethod.AES

        val protectedZipFile = ZipFile(zipFile, password.toCharArray())
        protectedZipFile.addFiles(files, zipParameters)
        protectedZipFile.close()
    }
}