package com.tutorial.learnenglishnewera.home_component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.reuseables.CustomizedText

@Composable
fun LastAddedWord(viewModel: MyViewModel){

    val dbList = remember { viewModel.jsonDbList }

    val lastItem by remember {
        derivedStateOf {
            dbList.lastOrNull()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            lastItem?.let {
                Log.i("myapp","recompose")
                CustomizedText(text = it.word, fontFamily = R.font.opensans_semicondensed_bold, fontSize = 20.sp)
                CustomizedText(text = it.phonetic, fontFamily = R.font.opensans_semicondensed_light, fontSize = 12.sp)
                CustomizedText(text = it.mean.joinToString(" | "), fontFamily = R.font.opensans_semicondensed_medium, fontSize = 16.sp)
            }
        }
    }
}

