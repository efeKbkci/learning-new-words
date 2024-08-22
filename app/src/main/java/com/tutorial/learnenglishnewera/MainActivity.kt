package com.tutorial.learnenglishnewera

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.tutorial.learnenglishnewera.navigation.BottomNavigation
import com.tutorial.learnenglishnewera.ui.theme.LearnEnglishNewEraTheme
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class MainActivity : ComponentActivity() {

    private val viewModel by lazy{ MyViewModel() }
    private val version = "v1.3" /*
    TODO:Uygulamayı güncellediğinde versiyon bilgisini,
     gradle dosyasındaki versiyonu ve github tagini güncelle, hepsi aynı olsun.
     Özellikle buradaki version ve github tagi çok önemli
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnEnglishNewEraTheme {
                BottomNavigation(viewModel)
            }
        }

        viewModel.startActivityEvent.observe(this){
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(viewModel.versionUrl)
            startActivity(intent)
        }

        requestPermission()
        versionControl()
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET
            ),
            0
        )
    }

    private fun versionControl(){

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.github.com/repos/efeKbkci/learning-new-words/releases/latest")
            .build()

        client.newCall(request).enqueue(object :Callback {
            override fun onFailure(call: Call, e: IOException) {
                viewModel.showSnackBar("Version couldn't check")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    val jsonElement = Json.parseToJsonElement(response.body.string())
                    val jsonObject = jsonElement.jsonObject
                    val tag = jsonObject["tag_name"]?.jsonPrimitive?.content
                    val latestUrl = jsonObject["html_url"]?.jsonPrimitive?.content

                    if (tag != null){
                        if (tag != version){
                            latestUrl?.let {
                                viewModel.versionUrl = it
                                viewModel.showUpdateMessenger(true)
                            }
                        }
                    }

                } else viewModel.showSnackBar("Version couldn't check -> ${response.code}")
            }
        })
    }
}