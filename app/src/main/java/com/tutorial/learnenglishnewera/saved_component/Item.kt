package com.tutorial.learnenglishnewera.saved_component

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.SpatialAudioOff
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.database.DbObject
import com.tutorial.learnenglishnewera.reuseables.CustomizedText
import kotlinx.coroutines.runBlocking
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Item(modifier: Modifier=Modifier ,viewModel: MyViewModel, dbObject: DbObject,  goToWord:()->Unit){

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(128.dp),
        onClick = {
            viewModel.currentDbObject = dbObject
            goToWord()
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
        ){
            if (dbObject.imagePath.isNotEmpty()){
                Image(
                    bitmap = BitmapFactory.decodeFile(dbObject.imagePath).asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    alpha = 0.38f
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Column{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f), verticalAlignment = Alignment.CenterVertically){
                            CustomizedText(
                                text = dbObject.word,
                                fontFamily = R.font.opensans_semicondensed_bold,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            Icon(
                                imageVector = Icons.Outlined.SpatialAudioOff,
                                contentDescription = "sound",
                                modifier = Modifier.clickable {
                                    viewModel.audioPlayer.apply {
                                        if (dbObject.pronunciationPath.isNotEmpty()) {
                                            stop()
                                            createPlayer(File(dbObject.pronunciationPath))
                                            start()
                                        }
                                    }
                                }
                            )
                        }
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "delete", modifier = Modifier.clickable {
                            runBlocking{ viewModel.dbProcess.removeItem(dbObject) }
                        })
                    }

                    CustomizedText(
                        text = dbObject.phonetic,
                        fontFamily = R.font.opensans_semicondensed_light,
                        fontSize = 14.sp
                    )
                }

                CustomizedText(text = dbObject.mean.joinToString(" | "), fontFamily = R.font.opensans_semicondensed_medium, fontSize = 17.sp)

                CustomizedText(
                    text = if (dbObject.exampleSentences.isNotEmpty()) dbObject.exampleSentences.random() else "",
                    fontFamily = R.font.opensans_semicondensed_lightitalic,
                    fontSize = 16.sp
                )
            }

        }
    }
}
