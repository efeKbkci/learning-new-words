package com.tutorial.learnenglishnewera.reuseables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMessenger(viewModel: MyViewModel){

    val updateState by viewModel.updateMessengerState.collectAsState()

    if (updateState){
        AlertDialog(
            onDismissRequest = { viewModel.showUpdateMessenger(false) },
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth(0.87f)
                    .height(173.dp),
                shape = RoundedCornerShape(5),
                onClick = {  }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomizedText(
                        text = "Yeni Versiyon Mevcut", 
                        fontFamily = R.font.lato_regular,
                        fontSize = 20.sp
                    )
                    
                    CustomizedText(
                        text = "Güncelle butonuna basarak son versiyonun bulunduğu github linkine gidebilirsiniz.", 
                        fontFamily = R.font.lato_light,
                        fontSize = 16.sp
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        ElevatedButton(
                            onClick = { viewModel.showUpdateMessenger(false) },
                            shape = RoundedCornerShape(5)
                        ) {
                            CustomizedText(text = "Sonra", fontFamily = R.font.plusjakarta_regular)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        ElevatedButton(
                            onClick = {
                                viewModel.startProcess()
                                viewModel.showUpdateMessenger(false)
                            },
                            shape = RoundedCornerShape(5)
                        ) {
                            CustomizedText(text = "Güncelle", fontFamily = R.font.plusjakarta_regular)
                        }
                    }
                }
            }
        }
    }

}