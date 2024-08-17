package com.tutorial.learnenglishnewera.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.home_component.AddedWords
import com.tutorial.learnenglishnewera.reuseables.CustomizedText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MyViewModel, goToWord:()->Unit){

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
                AddedWords(viewModel = viewModel)
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.currentDbObject = null // yeni bir obje ekleneceği zaman null, obje düzenleneceği zaman o obje atanır
                        goToWord()
                    },
                    shape = RoundedCornerShape(20)
                ) {
                    CustomizedText(
                        text = "New Word",
                        fontFamily = R.font.plusjakarta_regular,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}