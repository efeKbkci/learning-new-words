package com.tutorial.learnenglishnewera

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutorial.learnenglishnewera.api.GetPhonetic
import com.tutorial.learnenglishnewera.audio.AudioPlayer
import com.tutorial.learnenglishnewera.database.DataBaseProcess
import com.tutorial.learnenglishnewera.database.DbObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyViewModel:ViewModel() {

    val jsonDbList = mutableStateListOf<DbObject>()

    val dbProcess = DataBaseProcess(this)

    val getPhonetic = GetPhonetic(this)

    val audioPlayer = AudioPlayer()

    var currentDbObject:DbObject? = null

    var enableNavigation = mutableStateOf(true)

    var previousRoute = "home" // home için ayrı, saved için ayrı bir lambda tanımlanacak

    private val _snackbarState = MutableStateFlow( SnackbarHostState() )
    val snackbarState: StateFlow<SnackbarHostState> = _snackbarState
    fun showSnackBar(text:String){
        viewModelScope.launch { _snackbarState.value.showSnackbar(text) }
    }

    var versionUrl:String = ""

    private val _startActivityEvent = MutableLiveData<Unit>()
    val startActivityEvent:LiveData<Unit> = _startActivityEvent
    fun startProcess(){
        _startActivityEvent.value = Unit
    }

    private val _updateMessengerState = MutableStateFlow( false )
    val updateMessengerState:StateFlow<Boolean> = _updateMessengerState
    fun showUpdateMessenger(value:Boolean){
        _updateMessengerState.value = value
    }

}