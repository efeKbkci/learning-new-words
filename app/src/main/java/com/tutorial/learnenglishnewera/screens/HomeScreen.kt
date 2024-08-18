package com.tutorial.learnenglishnewera.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.home_component.AddedWords
import com.tutorial.learnenglishnewera.reuseables.CustomizedText
import com.tutorial.learnenglishnewera.reuseables.ModernBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MyViewModel, goToWord:()->Unit){

    ModernBackground {
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

