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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.database.DbObject
import com.tutorial.learnenglishnewera.navigation.AllDestinations
import com.tutorial.learnenglishnewera.reuseables.CustomizedText
import com.tutorial.learnenglishnewera.reuseables.CustomizedTextField
import com.tutorial.learnenglishnewera.word_component.HandleResponse
import com.tutorial.learnenglishnewera.word_component.MultipleInsertion
import com.tutorial.learnenglishnewera.word_component.loadImageFromURL
import com.tutorial.learnenglishnewera.word_component.saveBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * WordScreen'e ulaşmanın 3 yolu vardır:
 *  New Word Button -> currentDbObject = null, Append butonuna basıldığında eleman eklenir
 *  SavedScreen'de Item'a basmak -> currenDbObject = item, Append butonua basıldığında eleman güncellenir
 *  HomeScreen'de Item'a basmak -> currenDbObject = item, Append butonuna basılamayacak çünkü sayfa disable olacak
 *  **/

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
    var soundUrl by remember { mutableStateOf("") } // User can customize his sound file
    // if the api involves a sound url, by default, it will be assign to this value

    var word by remember { mutableStateOf("") }
    val meanList = remember { mutableStateListOf<String>() }
    var phonetic by remember { mutableStateOf("") }
    val context = remember { mutableStateListOf<String>() }
    val exampleSentences = remember { mutableStateListOf<String>() }
    var imagePath by remember { mutableStateOf("") }
    var isItLearned by remember { mutableStateOf(false) }

    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var screenIsDisabled by remember{ mutableStateOf(false) }

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
            soundFilePath = it.pronunciationPath
            imagePath = it.imagePath
            isItLearned = it.isItLearned
        }

        if (currentItem != null && viewModel.previousRoute == AllDestinations.HOME){
            screenIsDisabled = true
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
            onTrailingIcon = { if(!screenIsDisabled) fetchData = fetchData.not() },
            trailingIconEnabled = !screenIsDisabled,
            enabled = !screenIsDisabled
        ) { word = it }

        MultipleInsertion(
            screenIsDisabled = screenIsDisabled,
            label = "Mean",
            value = addMean,
            onValueChange = {addMean = it },
            list = meanList
        )

        CustomizedTextField(
            value = phonetic,
            label = "Phonetic",
            trailingIcon = Icons.Outlined.PlayCircleOutline,
            onTrailingIcon = {
                if (soundFilePath.isNotEmpty()){
                    viewModel.audioPlayer.apply {
                        stop()
                        createPlayer(File(soundFilePath))
                        start()
                    }
                }
            },
            trailingIconEnabled = playerIsActive,
            enabled = !screenIsDisabled
        ) { phonetic = it }

        CustomizedTextField(
            value = soundUrl,
            label = "Pronunciation URL",
            trailingIcon = Icons.AutoMirrored.Outlined.Send,
            onTrailingIcon = {
                if (soundUrl.isNotEmpty()){
                    CoroutineScope(Dispatchers.IO).launch {
                        val filePath = viewModel.getPhonetic.downloadSound(soundUrl)
                        if (filePath.isNotEmpty()){
                            withContext(Dispatchers.Main){
                                soundFilePath = filePath
                                viewModel.audioPlayer.createPlayer(File(soundFilePath))
                            }
                        }
                    }
                }
            },
            trailingIconEnabled = playerIsActive,
            enabled = !screenIsDisabled
        ) { soundUrl = it }

        MultipleInsertion(
            screenIsDisabled = screenIsDisabled,
            label = "Add Context",
            value = addContext,
            onValueChange = {addContext = it},
            list = context
        )

        MultipleInsertion(
            screenIsDisabled = screenIsDisabled,
            label = "Add Sentence",
            value = addSentence,
            onValueChange = {addSentence = it},
            list = exampleSentences
        )

        CustomizedTextField(
            value = imageURL,
            label = "Image Path",
            trailingIcon = Icons.Outlined.Image,
            onTrailingIcon = {
                if (!screenIsDisabled) loadImageFromURL(viewModel = viewModel, url = imageURL){ bitmap = it }
            },
            enabled = !screenIsDisabled
            // fonksyion içerisinde çalışan coroutine scope sonlanınca state'i güncelleyecek ve otomatikmen resim sayfaya yüklenecek
        ) { imageURL = it }

        if (bitmap != null || imagePath.isNotEmpty()){
            // bitmap null değilse kaydedilmemiş resim görüntülenir, imagePath boş değilse kaydedilmiş resim görüntülenir
            ElevatedCard{
                Box{
                    Image(
                        bitmap = bitmap ?: BitmapFactory.decodeFile(imagePath).asImageBitmap(),
                        contentDescription = null,
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
            Checkbox(checked = isItLearned, enabled = !screenIsDisabled,onCheckedChange = { isItLearned = it })
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            CustomizedText(text = "Disable Bottom Navigation", fontFamily = R.font.opensans_semicondensed_medium)
            Checkbox(checked = enableNavigation.not(), onCheckedChange = { enableNavigation = it.not() }, enabled = !screenIsDisabled)
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .graphicsLayer { alpha = if (screenIsDisabled) 0f else 1f },
            enabled = !screenIsDisabled,
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

                // eğer word screen'e home penceresindeki butondan ulaşmışsak veri ekleyeceğiz
                // Saved screen'deki bir elemana tıkladıysak veri güncelleyeceğiz
                if (viewModel.previousRoute == AllDestinations.HOME) {
                    CoroutineScope(Dispatchers.IO).launch { viewModel.dbProcess.addItem(dbObject) }
                }
                else if (viewModel.previousRoute == AllDestinations.SAVED) {
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
        onSoundUrl = {soundUrl = it},
        viewModel = viewModel
    )
}
