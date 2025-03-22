package com.example.arabus.ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


fun downloadDocument(context: Context, fileName: String) {
    try {
        Log.d("DownloadDocument", "Tentando abrir o arquivo: $fileName")

        val inputStream: InputStream = context.assets.open(fileName)

        val outputFile = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
        val outputStream = FileOutputStream(outputFile)

        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }

        outputStream.flush()
        outputStream.close()
        inputStream.close()

        Log.d("DownloadDocument", "Arquivo salvo em: ${outputFile.absolutePath}")

        val uri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", outputFile)

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(intent)

    } catch (e: Exception) {
        Log.e("DownloadDocument", "Erro ao baixar documento", e)
    }
}
