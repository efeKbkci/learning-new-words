package com.tutorial.learnenglishnewera

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.tutorial.learnenglishnewera.database.DbObject
import com.tutorial.learnenglishnewera.navigation.BottomNavigation
import com.tutorial.learnenglishnewera.ui.theme.LearnEnglishNewEraTheme
import java.io.File

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnEnglishNewEraTheme {
                BottomNavigation(viewModel = MyViewModel())
            }
        }
        requestPermission()
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET
            ),
            0
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LearnEnglishNewEraTheme {

    }
}