package com.tutorial.learnenglishnewera.test_component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.database.DbObject
import com.tutorial.learnenglishnewera.reuseables.CustomizedText

enum class Goal{
    Mean,
    Sentence
}

@Composable
fun ShowHide(item:DbObject , goal:Goal, show:Boolean, onShow:(Boolean)->Unit){

    var changeSentence by remember { mutableStateOf(false) }

    val randomSentence = remember(item) { RandomValue(item.exampleSentences) }

    val value by remember(item, changeSentence) {
        mutableStateOf(
            when (goal){
                Goal.Mean -> item.mean.joinToString(" | ")
                Goal.Sentence -> randomSentence.random()
            }
        )
    }

    LaunchedEffect(key1 = item) { onShow(false) } // her yeni elemanda kutular kapatılır

    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable { if (goal == Goal.Sentence) changeSentence = changeSentence.not() },
            shape = RoundedCornerShape(20)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                CustomizedText(
                    text = value ?: "",
                    fontFamily = R.font.opensans_semicondensed_medium,
                    fontSize = 17.sp,
                    color = if(!show){ Color.Transparent } else Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.width(7.dp))
        
        Icon(
            imageVector = if (!show) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
            contentDescription = null,
            modifier = Modifier.clickable { onShow(show.not()) }
        )
    }
}