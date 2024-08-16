package com.tutorial.learnenglishnewera.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.reuseables.CustomizedText
import com.tutorial.learnenglishnewera.test_component.Buttons
import com.tutorial.learnenglishnewera.test_component.Goal
import com.tutorial.learnenglishnewera.test_component.ItemsImage
import com.tutorial.learnenglishnewera.test_component.RandomValue
import com.tutorial.learnenglishnewera.test_component.ShowHide
import com.tutorial.learnenglishnewera.word_component.MultipleInsertion
import kotlinx.coroutines.runBlocking
import java.io.File

@Composable
fun TestScreen(viewModel: MyViewModel){

    /** Testi başlatmak için kullanılan state **/
    var startTest by remember{ mutableStateOf(false) }
    /** Teste yeniden başlanıldığı zaman, önceki testte öğrenildi olarak işaretlenen kelimeler yeni teste dahil edilmemesi için **/
    val dbList = remember(startTest){ viewModel.jsonDbList.filter { !it.isItLearned }.toMutableStateList() }
    val btnEnabled by remember { derivedStateOf { dbList.isNotEmpty() } }
    /****/
    val randomValue = remember { RandomValue(dbList) }
    var item by remember { mutableStateOf(randomValue.random()) }
    /****/
    var showMean by remember{ mutableStateOf(false) }
    var showSentence by remember{ mutableStateOf(false) }
    /** eklenen yeni cümleler için kullanılan stateler **/
    var newSentence by remember{ mutableStateOf("") }
    val newSentenceList = remember{ mutableStateListOf<String>() }

    /** Lambda fonksiyonlar **/

    val prepareAudio = { path:String ->
        viewModel.audioPlayer.apply {
            stop()
            if(path.isNotEmpty()) {
                createPlayer(File(path))
            }
        }
    }

    val prepareValues = {
        item = randomValue.random()
        prepareAudio(item!!.pronunciationPath)
        newSentence = ""
        newSentenceList.clear()
    }

    val startFun = {
        prepareValues()
        randomValue.resetUsedList()
        startTest = true
    }

    val nextItemFun = {
        if (randomValue.endOfTheList()) {
            startTest = false
        } else {
            if (newSentenceList.isNotEmpty()){
                val oldItem = item!!.copy()
                item!!.exampleSentences.forEach{ newSentenceList.add(it) }
                item!!.exampleSentences = newSentenceList.toList()
                runBlocking{ viewModel.dbProcess.updateItem(oldItem, item!!) }
            }
            prepareValues()
        }
    }

    /** Son **/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 96.dp)
            .verticalScroll(rememberScrollState(), true),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!startTest){
            Button(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = startFun,
                shape = RoundedCornerShape(20),
                enabled = btnEnabled
            ) {
                CustomizedText(
                    text = "Start Test",
                    fontFamily = R.font.opensans_semicondensed_bold,
                    color = Color(0xFFffffff)
                )
            }
        }
        else{
            CustomizedText(
                text = item!!.word,
                fontFamily = R.font.opensans_semicondensed_bold,
                fontSize = 24.sp,
                modifier = Modifier.clickable {
                    viewModel.audioPlayer.apply {
                        if (isActive()) start()
                    }
                }
            )

            CustomizedText(text = item!!.phonetic, fontFamily = R.font.opensans_semicondensed_light, fontSize = 14.sp)

            if (item!!.imagePath.isNotEmpty()){
                ItemsImage(imagePath = item!!.imagePath)
            }

            ShowHide(item = item!!, goal = Goal.Mean, show = showMean) {
                showMean = it
            }

            ShowHide(item = item!!, goal = Goal.Sentence, show = showSentence) {
                showSentence = it
            }

            MultipleInsertion(
                modifier = Modifier.fillMaxWidth(0.9f),
                label = "New Sentence",
                value = newSentence,
                onValueChange = {newSentence =it},
                list = newSentenceList
            )

            Buttons(
                onFinish = { startTest = false },
                onNext = nextItemFun
            )
        }
    }

}