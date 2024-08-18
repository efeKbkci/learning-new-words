package com.tutorial.learnenglishnewera.api

import android.os.Environment
import android.util.Log
import com.tutorial.learnenglishnewera.MyViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream

class GetPhonetic(private val viewModel: MyViewModel) {

    private val endPoint = "https://api.dictionaryapi.dev/api/v2/entries/en/"

    private val client = OkHttpClient()

    private val folderPath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "pronunciations")

    private lateinit var soundFileUrl:String

    init {
        pathControl()
    }

    private fun pathControl():Boolean{
        if (!folderPath.exists()) {
            val isCreated = folderPath.mkdirs()
            if (isCreated) {
                println("Klasör başarıyla oluşturuldu: ${folderPath.absolutePath}")
                return true
            } else {
                println("Klasör oluşturulamadı.")
                return false
            }
        } else {
            println("Klasör zaten mevcut: ${folderPath.absolutePath}")
            return true
        }
    }

    fun handleJson(word:String):Response{

        Log.i("myapp","URL -> $endPoint$word")

        val request = Request.Builder()
            .url("$endPoint$word")
            .build()

        return client.newCall(request).execute()
    }

    fun downloadSound():String{
        if (soundFileUrl.isEmpty()) {
            viewModel.showSnackBar("Response is not contain any audio files")
            return ""
        }

        val fileName = soundFileUrl.split("/").last()

        val request = Request.Builder()
            .url(soundFileUrl)
            .build()

        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {

            val inputStream = response.body.byteStream()
            val outputStream = FileOutputStream(File(folderPath, fileName))

            try {
                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                        Log.i("myapp", "dosya başarıyla indirildi")
                    }
                }
            } catch (e: Exception) {
                viewModel.showSnackBar("Audio file couldn't save")
                e.printStackTrace()
            }

            val file = File(folderPath, fileName)
            if (file.length() == 0L){
                file.delete()
                viewModel.showSnackBar("Audio file is corrupted, has been deleted")
                ""
            }
            else file.absolutePath
        } else ""
    }

    fun extractPhonetic(list: List<Phonetic>):String{

        var phonetic = ""
        var audioURL = ""

        list.forEach {
            if (it.text != null) phonetic = it.text
            if (it.audio != null) audioURL = it.audio
        }

        soundFileUrl = audioURL

        return phonetic
    }
}