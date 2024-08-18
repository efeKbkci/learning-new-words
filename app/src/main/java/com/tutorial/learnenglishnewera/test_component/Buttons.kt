package com.tutorial.learnenglishnewera.test_component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.reuseables.CustomizedText

@Composable
fun Buttons(onFinish:()->Unit, onNext:()->Unit){
    Row {
        Button(
            modifier = Modifier.width(120.dp),
            onClick = {onFinish()},
            shape = RoundedCornerShape(20)
        ) {
            CustomizedText(
                text = "Finish",
                fontFamily = R.font.opensans_semicondensed_bold,
                color = Color(0xFFffffff
                )
            )
        }
        Spacer(modifier = Modifier.width(7.dp))

        Button(
            modifier = Modifier.width(120.dp),
            onClick = {onNext()},
            shape = RoundedCornerShape(20)
        ) {
            CustomizedText(
                text = "Next",
                fontFamily = R.font.opensans_semicondensed_bold,
                color = Color(0xFFffffff
                )
            )
        }
    }

}