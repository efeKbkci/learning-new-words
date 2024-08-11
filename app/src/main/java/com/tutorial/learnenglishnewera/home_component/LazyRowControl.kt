package com.tutorial.learnenglishnewera.home_component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tutorial.learnenglishnewera.database.DbObject

@Composable
fun LazyRowControl(
    currentIndex:Int,
    onCurrentIndex:(Int)->Unit,
    list: SnapshotStateList<DbObject>,
    autoScroll:Boolean,
    onAutoScroll:(Boolean) -> Unit
){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onClick = { if (currentIndex == 0) onCurrentIndex(list.lastIndex) else onCurrentIndex(currentIndex-1)}) {
            Icon(
                imageVector = Icons.Filled.ChevronLeft,
                contentDescription = null
            )
        }
        IconButton(onClick = { onAutoScroll(autoScroll.not())  }) {
            Icon(
                imageVector = Icons.Filled.Circle,
                contentDescription = null,
                tint = if (autoScroll) Color(0xffaaa69d) else Color(0xffd1ccc0)
            )
        }
        IconButton(onClick = { if (currentIndex == list.lastIndex) onCurrentIndex(0) else onCurrentIndex(currentIndex+1)}) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null
            )
        }
    }
}