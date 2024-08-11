package com.tutorial.learnenglishnewera

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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

    var previousRoute = "home" // home için ayrı, saved için ayrı bir lambda tanımlanacak
}