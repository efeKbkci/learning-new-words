package com.tutorial.learnenglishnewera.reuseables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.R

@Composable
fun CustomizedTextField(
    modifier: Modifier = Modifier,
    label:String="",
    supportingText:String="",
    value:String,
    leadingIcon: ImageVector?=null,
    onLeadingIcon:()->Unit={},
    leadingIconEnabled:Boolean = true,
    trailingIcon: ImageVector?=null,
    onTrailingIcon:()->Unit={},
    trailingIconEnabled:Boolean = true,
    justNumbers:Boolean=false,
    isError:Boolean=false,
    enabled:Boolean=true,
    shape:Shape?=null,
    colors:TextFieldColors?=null,
    onValueChange:(String)->Unit
) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                Modifier.background(Color.Transparent),
                fontFamily = FontFamily(Font(R.font.opensans_semicondensed_medium))
            )
        },
        supportingText = {
            Text(
                text = supportingText,
                modifier = Modifier.background(Color.Transparent),
                fontFamily = FontFamily(Font(R.font.opensans_semicondensed_medium))
            )
        },
        leadingIcon = leadingIcon?.let {
            { Icon(
                imageVector = leadingIcon,
                contentDescription = "",
                modifier = Modifier.clickable { if (!isError || leadingIconEnabled) onLeadingIcon() })
            }
        },
        trailingIcon = trailingIcon?.let{
            { Icon(
                imageVector = trailingIcon,
                contentDescription = "",
                modifier = Modifier.clickable { if (!isError || trailingIconEnabled) onTrailingIcon() })
            }
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.opensans_semicondensed_medium))
        ),
        isError = isError,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
        shape = shape ?: RoundedCornerShape(5),
        keyboardOptions = KeyboardOptions(keyboardType = if (justNumbers) KeyboardType.Number else KeyboardType.Text),
        colors = colors ?: OutlinedTextFieldDefaults.colors()
    )
}