package com.tutorial.learnenglishnewera.word_component

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.tutorial.learnenglishnewera.MyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL


fun loadImageFromURL(viewModel: MyViewModel, url:String, onImageBitmap:(ImageBitmap?)->Unit){
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.inputStream.use {
                onImageBitmap(BitmapFactory.decodeStream(it).asImageBitmap())
            }
        } catch (e:Exception){
            Log.e("myapp","resim indirilemedi",e)
            viewModel.showSnackBar("Image couldn't download, use another image url")
            onImageBitmap(null)
        }
    }
}