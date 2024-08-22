package com.tutorial.learnenglishnewera.home_component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.database.DbObject
import kotlinx.coroutines.delay

@Composable
fun ColumnScope.AddedWords(viewModel: MyViewModel, goToWord:()->Unit){

    val jsonDbList = remember { viewModel.jsonDbList }
    val visibleList = remember { mutableStateListOf<DbObject>() }
    var infinitiLoop by remember{ mutableStateOf(true) }

    LaunchedEffect(infinitiLoop) {
        jsonDbList.chunked(3).forEach { chunk ->
            chunk.forEach { visibleList.add(it) }
            delay(3000)
            visibleList.clear()
            delay(500)
        }
        infinitiLoop = infinitiLoop.not()
    }

    Column(
        modifier = Modifier.fillMaxSize().weight(1f),
        verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ){
        visibleList.forEachIndexed{ index, dbObject ->
            AnimatedCard(viewModel = viewModel, dbObject = dbObject, durationMs = ((index+1)*400).toLong()){ goToWord() }
        }
    }
}