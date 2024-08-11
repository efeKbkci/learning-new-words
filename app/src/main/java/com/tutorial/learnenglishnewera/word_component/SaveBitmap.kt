package com.tutorial.learnenglishnewera.word_component

import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

fun saveBitmap(bitmap: Bitmap, fileName:String):String?{
    return try {
        val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "word_images")
        if (!folder.exists()) folder.mkdirs()
        val file = File(folder, "$fileName.png")
        if (!file.exists()) file.createNewFile()
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        file.absolutePath
    } catch (e:Exception){
        null
    }
}