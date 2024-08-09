package com.tutorial.learnenglishnewera.home_component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Hexagon
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.database.DbObject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddedWords(viewModel: MyViewModel){

    val lazyListState = rememberLazyListState()
    //val jsonDbList = remember { viewModel.jsonDbList }
    val jsonDbList = remember {
        mutableStateListOf(
            "1.kelime",
            "2.kelime",
            "3.kelime"
        )
    }
    var currentItemIndex by remember { mutableIntStateOf(0) }
    var autoScroll by remember{ mutableStateOf(true) }

    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ){
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, start = 5.dp, end = 5.dp),
            state = lazyListState,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
//        items(jsonDbList, { item:DbObject -> item.objectID }){
//            Text(text = it.word)
//        }

            items(jsonDbList, { item: String -> item }) {
                Card(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(125.dp)
                ) {
                    Text(text = currentItemIndex.toString())
                }
            }
        }

        LazyRowControl(
            currentIndex = currentItemIndex,
            onCurrentIndex = { newValue -> currentItemIndex = newValue },
            list = jsonDbList,
            autoScroll = autoScroll,
            onAutoScroll = { newValue -> autoScroll = newValue }
        )

        LaunchedEffect(currentItemIndex, autoScroll) {
            if (jsonDbList.isNotEmpty() && autoScroll) {
                lazyListState.animateScrollToItem(currentItemIndex)
                delay(1500)
                currentItemIndex = (currentItemIndex + 1) % jsonDbList.size
            }
        }
    }
}