package com.tutorial.learnenglishnewera.ui.theme

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFCCD5AE),
    secondary = Color(0xffFAEDCD),
    tertiary = Color(0xffD4A373),
    background = Color(0xFFfefae0),
    surface = Color(0xffe9edc9)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun LearnEnglishNewEraTheme(
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {

    val inputStream = LocalContext.current.assets.open("colors.json")
    val jsonContent = inputStream.bufferedReader().use { it.readText() }
    val jsonElement = Json.parseToJsonElement(jsonContent)

    val customScheme = lightColorScheme(
        primary = Color(jsonElement.jsonObject["primary"]?.jsonPrimitive?.int!!),
        onPrimary = Color(jsonElement.jsonObject["onPrimary"]?.jsonPrimitive?.int!!),
        primaryContainer = Color(jsonElement.jsonObject["primaryContainer"]?.jsonPrimitive?.int!!),
        onPrimaryContainer = Color(jsonElement.jsonObject["onPrimaryContainer"]?.jsonPrimitive?.int!!),
        secondary = Color(jsonElement.jsonObject["secondary"]?.jsonPrimitive?.int!!),
        onSecondary = Color(jsonElement.jsonObject["onSecondary"]?.jsonPrimitive?.int!!),
        secondaryContainer = Color(jsonElement.jsonObject["secondaryContainer"]?.jsonPrimitive?.int!!),
        onSecondaryContainer = Color(jsonElement.jsonObject["onSecondaryContainer"]?.jsonPrimitive?.int!!),
        tertiary = Color(jsonElement.jsonObject["tertiary"]?.jsonPrimitive?.int!!),
        onTertiary = Color(jsonElement.jsonObject["onTertiary"]?.jsonPrimitive?.int!!),
        tertiaryContainer = Color(jsonElement.jsonObject["tertiaryContainer"]?.jsonPrimitive?.int!!),
        onTertiaryContainer = Color(jsonElement.jsonObject["onTertiaryContainer"]?.jsonPrimitive?.int!!),
        background = Color(jsonElement.jsonObject["background"]?.jsonPrimitive?.int!!),
        onBackground = Color(jsonElement.jsonObject["onBackground"]?.jsonPrimitive?.int!!),
        surface = Color(jsonElement.jsonObject["surface"]?.jsonPrimitive?.int!!),
        onSurface = Color(jsonElement.jsonObject["onSurface"]?.jsonPrimitive?.int!!),
        surfaceVariant = Color(jsonElement.jsonObject["surfaceVariant"]?.jsonPrimitive?.int!!),
        onSurfaceVariant = Color(jsonElement.jsonObject["onSurfaceVariant"]?.jsonPrimitive?.int!!),
        error = Color(jsonElement.jsonObject["error"]?.jsonPrimitive?.int!!),
        onError = Color(jsonElement.jsonObject["onError"]?.jsonPrimitive?.int!!),
        errorContainer = Color(jsonElement.jsonObject["errorContainer"]?.jsonPrimitive?.int!!),
        onErrorContainer = Color(jsonElement.jsonObject["onErrorContainer"]?.jsonPrimitive?.int!!),
        outline = Color(jsonElement.jsonObject["outline"]?.jsonPrimitive?.int!!),
        outlineVariant = Color(jsonElement.jsonObject["outlineVariant"]?.jsonPrimitive?.int!!),
        inverseOnSurface = Color(jsonElement.jsonObject["inverseOnSurface"]?.jsonPrimitive?.int!!),
        inverseSurface = Color(jsonElement.jsonObject["inverseSurface"]?.jsonPrimitive?.int!!),
        inversePrimary = Color(jsonElement.jsonObject["inversePrimary"]?.jsonPrimitive?.int!!),
        surfaceTint = Color(jsonElement.jsonObject["surfaceTint"]?.jsonPrimitive?.int!!)
    )

    MaterialTheme(
        colorScheme = customScheme,
        typography = Typography,
        content = content
    )
}