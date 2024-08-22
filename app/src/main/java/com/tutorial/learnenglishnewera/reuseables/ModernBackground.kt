package com.tutorial.learnenglishnewera.reuseables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.R
import java.time.LocalTime

// (ColumnScope.()->Unit) ifadesi ()->Unit lambda fonksiyonunun bir Column içerisinde çağırılacağını ifade eder ve lambda fonksiyonu
// bir ColumnScope alanı içerisinde başlatır.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernBackground(content:@Composable (ColumnScope.()->Unit)){

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter){
        Image(
            painter = painterResource(id = R.drawable.wallpaper_high_quality_day),
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 34.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomizedText(
                text = "Learn English",
                fontFamily = R.font.greyqo_regular,
                fontSize = 50.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))){

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { alpha = 0.57f }
                        .background(Color.White.copy(0.73f)),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                    onClick = {}
                ) {

                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, bottom = 112.dp, top = 24.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    content()
                }
            }
        }
    }
}