package com.tutorial.learnenglishnewera.home_component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.database.DbObject
import com.tutorial.learnenglishnewera.saved_component.Item
import kotlinx.coroutines.delay

@Composable
fun AnimatedCard(viewModel: MyViewModel, dbObject: DbObject, durationMs:Long, goToWord:()->Unit){

    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(durationMs)
        isVisible = true
    }

    LaunchedEffect(Unit) {
        delay(3000 - durationMs/2)
        isVisible = false
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically { -it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut()
    ) {
        Item(viewModel = viewModel, dbObject = dbObject) {
            viewModel.currentDbObject = dbObject
            goToWord()
        }
    }
}