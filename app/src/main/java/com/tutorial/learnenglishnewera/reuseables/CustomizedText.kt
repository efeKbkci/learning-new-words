package com.tutorial.learnenglishnewera.reuseables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun CustomizedText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Left,
    fontSize:TextUnit = 14.sp,
    fontFamily:Int,
    color: Color = Color(0xFF000000)
) {

    val style = TextStyle(
        fontSize = fontSize,
        textAlign = textAlign,
        fontFamily = FontFamily(Font(fontFamily)),
        color = color
    )

    Text(text = text, style = style, modifier = modifier)
}