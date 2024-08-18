package com.tutorial.learnenglishnewera.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.database.DbObject
import com.tutorial.learnenglishnewera.reuseables.CustomizedText
import com.tutorial.learnenglishnewera.reuseables.CustomizedTextField
import com.tutorial.learnenglishnewera.word_component.HandleResponse
import com.tutorial.learnenglishnewera.word_component.MultipleInsertion
import com.tutorial.learnenglishnewera.word_component.loadImageFromURL
import com.tutorial.learnenglishnewera.word_component.saveBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun WordScreen(viewModel: MyViewModel, goToSaved:()->Unit){

    val currentItem = viewModel.currentDbObject

    var addContext by remember { mutableStateOf("") }
    var addSentence by remember { mutableStateOf("") }
    var addMean by remember { mutableStateOf("") }
    var soundFilePath by remember { mutableStateOf("") }

    var enableNavigation by remember { viewModel.enableNavigation }
    var fetchData by rememberSaveable { mutableStateOf(true) }
    var playerIsActive by rememberSaveable { mutableStateOf(false) }
    var imageURL by remember { mutableStateOf("") }

    var word by remember { mutableStateOf("") }
    val meanList = remember { mutableStateListOf<String>() }
    var phonetic by remember { mutableStateOf("") }
    val context = remember { mutableStateListOf<String>() }
    val exampleSentences = remember { mutableStateListOf<String>() }
    var imagePath by remember { mutableStateOf("") }
    var isItLearned by remember { mutableStateOf(false) }

    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    /*
    * bitmap state = resim kaynaktan ilk alındığında herhangi bir yere kaydedilmeden doğrudan görüntülenen halidir
    * imagePath state = kelime kaydedilmeye karar verilirse bitmap state bir dizine kaydedilir ve kaydedilen dosyanın yolu
    * image path state'tine eşit olur.
    */

    LaunchedEffect(Unit) {
        currentItem?.let {
            word = it.word
            it.mean.forEach { mean -> meanList.add(mean) }
            phonetic = it.phonetic
            it.context.forEach { contextItem -> context.add(contextItem) }
            it.exampleSentences.forEach { sentence -> exampleSentences.add(sentence) }
            imagePath = it.imagePath
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

        MultipleInsertion(label = "Mean", value = addMean, onValueChange = {addMean = it }, list = meanList)

        CustomizedTextField(
            value = phonetic,
            label = "Phonetic",
            trailingIcon = Icons.Outlined.PlayCircleOutline,
            onTrailingIcon = { viewModel.audioPlayer.start() },
            trailingIconEnabled = playerIsActive
        ) { phonetic = it }

        MultipleInsertion(label = "Add Context", value = addContext, onValueChange = {addContext = it}, list = context)

        MultipleInsertion(label = "Add Sentence", value = addSentence, onValueChange = {addSentence = it}, list = exampleSentences)

        CustomizedTextField(
            value = imageURL,
            label = "Image Path",
            trailingIcon = Icons.Outlined.Image,
            onTrailingIcon = { loadImageFromURL(viewModel = viewModel, url = imageURL){ bitmap = it } }
            // fonksyion içerisinde çalışan coroutine scope sonlanınca state'i güncelleyecek ve otomatikmen resim sayfaya yüklenecek
        ) { imageURL = it }

        if (bitmap != null || imagePath.isNotEmpty()){
            // bitmap null değilse kaydedilmemiş resim görüntülenir, imagePath boş değilse kaydedilmiş resim görüntülenir
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Box(modifier = Modifier.fillMaxSize()){
                    Image(
                        bitmap = bitmap ?: BitmapFactory.decodeFile(imagePath).asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }

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

                if (bitmap != null){
                    val filePath = saveBitmap(bitmap!!.asAndroidBitmap(), word)
                    if (filePath != null) imagePath = filePath
                }
                // else durumlarında imagePath daima "" olacak.

                val dbObject = DbObject(
                    objectID = System.currentTimeMillis(),
                    word = word,
                    mean = meanList.toList(),
                    phonetic = phonetic,
                    imagePath = imagePath,
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

    // word textfield'ı içerisindeki butona basıldığında HandleResponse içerisindeki Launched Effect çalışır.
    // İstek oluşturulur ve cevap işlenir.

    HandleResponse(
        fetchData = fetchData,
        word = word,
        onPhonetic = {phonetic = it},
        onPlayerIsActive = {playerIsActive = it },
        onSoundFilePath = {soundFilePath = it},
        viewModel = viewModel
    )
}
