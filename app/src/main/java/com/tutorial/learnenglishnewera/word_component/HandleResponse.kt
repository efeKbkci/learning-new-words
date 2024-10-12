package com.tutorial.learnenglishnewera.word_component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.api.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File

@Composable
fun HandleResponse(
    fetchData:Boolean,
    word:String,
    onPhonetic:(String)->Unit,
    onSoundUrl:(String)->Unit,
    viewModel: MyViewModel
){
    LaunchedEffect(key1 = fetchData) {

        if (word.isEmpty()) return@LaunchedEffect

        if (viewModel.audioPlayer.isActive()) viewModel.audioPlayer.stop()

        CoroutineScope(Dispatchers.IO).launch {
            val response = viewModel.getPhonetic.handleJson(word) // api çağrısı yapılır, json verisi alınır
            if (response.isSuccessful) {
                val apiResponse: ApiResponse? = Json.decodeFromString<List<ApiResponse>>(response.body.string()).firstOrNull()
                // api çağrısı başarılıysa ApiResponse data class'ı şeklinde decode edilir
                if (apiResponse == null){
                    viewModel.showSnackBar("Response is empty, your word has no equivalent")
                } else {
                    val extractedPhonetic = viewModel.getPhonetic.extractPhonetic(apiResponse.phonetics)
                    // Phonetic data class'ından uygun phonetic bilgisi alınır, ses dosyasının url'si çıkarılır
                    withContext(Dispatchers.Main){
                        onPhonetic(extractedPhonetic)
                        onSoundUrl(viewModel.getPhonetic.soundFileUrl)
                    }
                    // main dispatcher'a geçilir ve arayüz güncellenir
                }
            }
            else viewModel.showSnackBar("Request has failed -> ${response.code}")
        }
    }
}