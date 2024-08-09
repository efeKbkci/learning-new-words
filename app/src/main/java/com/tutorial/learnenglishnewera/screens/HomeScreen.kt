package com.tutorial.learnenglishnewera.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.home_component.AddedWords
import com.tutorial.learnenglishnewera.home_component.LastAddedWord
import com.tutorial.learnenglishnewera.reuseables.CustomizedText

@Composable
fun HomeScreen(viewModel: MyViewModel, goToWord:()->Unit){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 48.dp, bottom = 96.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddedWords(viewModel = viewModel)

        Button(
            modifier = Modifier.fillMaxWidth(0.8f),
            onClick = {
                viewModel.currentDbObject = null // yeni bir obje ekleneceği zaman null, obje düzenleneceği zaman o obje atanır
                goToWord()
            },
            shape = RoundedCornerShape(20)
        ) {
            CustomizedText(text = "New Word", fontFamily = R.font.opensans_semicondensed_bold, color = Color(0xFFF0F0F0)
            )
        }
        
        LastAddedWord(viewModel = viewModel)

    }
}