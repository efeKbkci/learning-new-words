package com.tutorial.learnenglishnewera.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.api.ApiResponse
import com.tutorial.learnenglishnewera.database.DbObject
import com.tutorial.learnenglishnewera.reuseables.CustomizedText
import com.tutorial.learnenglishnewera.reuseables.CustomizedTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File

@Composable
fun WordScreen(viewModel: MyViewModel, goToSaved:()->Unit){

    val currentItem = viewModel.currentDbObject

    var addContext by remember { mutableStateOf("") }
    var addSentence by remember { mutableStateOf("") }
    var soundFilePath by remember { mutableStateOf("") }

    var enableNavigation by remember { viewModel.enableNavigation }

    var fetchData by rememberSaveable { mutableStateOf(true) }
    var playerIsActive by rememberSaveable { mutableStateOf(false) }

    var word by remember { mutableStateOf("") }
    var mean by remember { mutableStateOf("") }
    var phonetic by remember { mutableStateOf("") }
    var context = remember { mutableStateListOf<String>() }
    var exampleSentences = remember { mutableStateListOf<String>() }
    var notes by remember { mutableStateOf("") }
    var isItLearned by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        currentItem?.let {
            word = it.word
            mean = it.mean
            phonetic = it.phonetic
            it.context.forEach { contextItem -> context.add(contextItem) }
            it.exampleSentences.forEach { sentence -> exampleSentences.add(sentence) }
            notes = it.notes
            isItLearned = it.isItLearned
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 96.dp)
            .verticalScroll(rememberScrollState(), true),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomizedTextField(
            value = word,
            label = "Word",
            trailingIcon = Icons.AutoMirrored.Outlined.Send,
            onTrailingIcon = { fetchData = fetchData.not() },
            // icona her tıklandığında fetchdata farklı bir değer alarak launched effect'i tetikleyecek
        ) { word = it }

        CustomizedTextField(value = mean, label = "Mean") { mean = it }

        CustomizedTextField(
            value = phonetic,
            label = "Phonetic",
            trailingIcon = Icons.Outlined.PlayCircleOutline,
            onTrailingIcon = { viewModel.audioPlayer.start() },
            trailingIconEnabled = playerIsActive
        ) { phonetic = it }

        ContextAndSentence(label = "Add Context", addValue = addContext, onAddValue = {addContext = it}, list = context)

        ContextAndSentence(label = "Add Sentence", addValue = addSentence, onAddValue = {addSentence = it}, list = exampleSentences)

        CustomizedTextField(value = notes, label = "Notes") { notes = it }

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            CustomizedText(text = "Is it learned?", fontFamily = R.font.opensans_semicondensed_medium)
            Checkbox(checked = isItLearned, onCheckedChange = { isItLearned = it })
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            CustomizedText(text = "Disable Bottom Navigation", fontFamily = R.font.opensans_semicondensed_medium)
            Checkbox(checked = enableNavigation.not(), onCheckedChange = { enableNavigation = it.not() })
        }

        Button(
            modifier = Modifier.fillMaxWidth(0.8f),
            onClick = {
                val dbObject = DbObject(
                    objectID = System.currentTimeMillis(),
                    word = word,
                    mean = mean,
                    phonetic = phonetic,
                    notes = notes,
                    exampleSentences = exampleSentences.toList(),
                    context = context.toList(),
                    isItLearned = isItLearned,
                    pronunciationPath = soundFilePath
                )

                // eğer word screen'e home penceresinden ulaşmışsak veri ekleyeceğiz
                // Saved screen'den ulaşmışsak veri güncelleyeceğiz
                if (viewModel.previousRoute == "home") CoroutineScope(Dispatchers.IO).launch { viewModel.dbProcess.addItem(dbObject) }
                else if (viewModel.previousRoute == "saved") {
                    CoroutineScope(Dispatchers.IO).launch { viewModel.dbProcess.updateItem(currentItem!!, dbObject) }
                }
                enableNavigation = true
                goToSaved()
            },
            shape = RoundedCornerShape(20)
        ) {
            CustomizedText(text = "Append", fontFamily = R.font.opensans_semicondensed_semibold, color = Color(0xFFFFFFFF))
        }
    }

    LaunchedEffect(key1 = fetchData) {

        if (word.isEmpty()) return@LaunchedEffect

        if (viewModel.audioPlayer.isActive()) viewModel.audioPlayer.stop()

        CoroutineScope(Dispatchers.IO).launch {
            val response = viewModel.getPhonetic.handleJson(word) // api çağrısı yapılır, json verisi alınır
            val apiResponse: ApiResponse? = Json.decodeFromString<List<ApiResponse>>(response.body.string()).firstOrNull()
            // api çağrısı başarılıysa ApiResponse data class'ı şeklinde decode edilir
            if (apiResponse == null){
                //TODO
            } else {
                val extractedPhonetic = viewModel.getPhonetic.extractPhonetic(apiResponse.phonetics)
                // Phonetic data class'ından uygun phonetic bilgisi alınır, ses dosyasının url'si çıkarılır
                withContext(Dispatchers.Main){ phonetic = extractedPhonetic }
                // main dispatcher'a geçilir ve arayüz güncellenir
                val soundFile = viewModel.getPhonetic.downloadSound()
                // ses dosyası indirilir ve ses dosyasının konumu alınır.
                if (soundFile.isNotEmpty()){
                    viewModel.audioPlayer.createPlayer(File(soundFile)) // player oluşturulur
                    playerIsActive = viewModel.audioPlayer.isActive()
                    soundFilePath = soundFile
                }
            }
        }
    }
}

@Composable
fun ContextAndSentence(label:String ,addValue:String, onAddValue:(String) -> Unit, list:SnapshotStateList<String>){
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        CustomizedTextField(
            value = addValue,
            label = label,
            trailingIcon = Icons.Outlined.AddCircleOutline,
            onTrailingIcon = {
                if (addValue.isNotEmpty() && !list.contains(addValue)){
                    list.add(0,addValue)
                    onAddValue("")
                }
            }
        ) {
            onAddValue(it)
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(0.dp, 90.dp)
                .padding(start = 4.dp, end = 4.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(list, { it: String -> it.hashCode() }) {
                Row(modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(start = 5.dp, end = 7.dp)
                ){
                    CustomizedText(
                        text = it,
                        fontFamily = R.font.opensans_semicondensed_lightitalic,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete sentence", modifier = Modifier.clickable {
                        list.remove(it)
                    })
                }
                Divider()
            }
        }
    }
}
