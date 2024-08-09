package com.tutorial.learnenglishnewera

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.tutorial.learnenglishnewera.database.DataBaseProcess
import com.tutorial.learnenglishnewera.database.DbObject

class MyViewModel:ViewModel() {

    val dbProcess = DataBaseProcess(this)

    val jsonDbList = mutableStateListOf<DbObject>()

    var currentDbObject:DbObject? = null

    var enableNavigation = mutableStateOf(true)
}