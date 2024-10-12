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

    lateinit var soundFileUrl:String
        private set

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
    fun downloadSound(soundUrl:String):String{

        viewModel.showSnackBar("File is downloading -> $soundUrl")

        val fileName = soundUrl.split("/").last()

        val request = Request.Builder()
            .url(soundUrl)
            .build()

        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {

            val inputStream = response.body.byteStream()
            val outputStream = FileOutputStream(File(folderPath, fileName))

            try {
                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                        viewModel.showSnackBar("File has been sucessfully downloaded")
                    }
                }
            } catch (e: Exception) {
                viewModel.showSnackBar("Audio file couldn't be saved")
                e.printStackTrace()
            }

            val file = File(folderPath, fileName)
            if (file.length() == 0L){
                file.delete()
                viewModel.showSnackBar("Audio file is corrupted, has been deleted")
                ""
            }
            else file.absolutePath
        } else {
            viewModel.showSnackBar("File couldn't be downloaded")
            ""
        }
    }
}