package com.tutorial.learnenglishnewera

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.tutorial.learnenglishnewera.database.DataBaseProcess
import com.tutorial.learnenglishnewera.database.DbObject

class MyViewModel:ViewModel() {

    val dbProcess = DataBaseProcess(this)

    val jsonDbList = mutableStateListOf<DbObject>()
}