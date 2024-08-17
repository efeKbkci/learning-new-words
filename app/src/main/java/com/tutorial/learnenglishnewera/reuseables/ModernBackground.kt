package com.tutorial.learnenglishnewera.reuseables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.R

// (ColumnScope.()->Unit) ifadesi ()->Unit lambda fonksiyonunun bir Column içerisinde çağırılacağını ifade eder ve lambda fonksiyonu
// bir ColumnScope alanı içerisinde başlatır.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernBackground(modifier: Modifier=Modifier,content:@Composable (ColumnScope.()->Unit)){

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xff9ca4b6))
        .padding(top = 34.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomizedText(
            text = "Learn English",
            fontFamily = R.font.greyqo_regular,
            fontSize = 50.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        ElevatedCard(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            onClick = {}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    }
}