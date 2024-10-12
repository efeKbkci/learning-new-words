package com.tutorial.learnenglishnewera.test_component

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale

@Composable
fun ItemsImage(imagePath:String){
    ElevatedCard {
        Box{
            Image(
                bitmap = BitmapFactory.decodeFile(imagePath).asImageBitmap(),
                contentDescription = null
            )
        }
    }
}