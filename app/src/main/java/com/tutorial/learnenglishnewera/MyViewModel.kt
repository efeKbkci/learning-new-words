package com.tutorial.learnenglishnewera

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.tutorial.learnenglishnewera.api.GetPhonetic
import com.tutorial.learnenglishnewera.audio.AudioPlayer
import com.tutorial.learnenglishnewera.database.DataBaseProcess
import com.tutorial.learnenglishnewera.database.DbObject

class MyViewModel:ViewModel() {

    val jsonDbList = mutableStateListOf<DbObject>()

    val dbProcess = DataBaseProcess(this)

    val getPhonetic = GetPhonetic()

    val audioPlayer = AudioPlayer()

    var currentDbObject:DbObject? = null

    var enableNavigation = mutableStateOf(true)
}