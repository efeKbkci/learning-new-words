package com.tutorial.learnenglishnewera.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.database.DbObject
import com.tutorial.learnenglishnewera.reuseables.CustomizedText
import com.tutorial.learnenglishnewera.reuseables.CustomizedTextField
import com.tutorial.learnenglishnewera.saved_component.Item

@Composable
fun SavedScreen(viewModel: MyViewModel, goToWord:()->Unit){

    val jsonDbList = remember{ viewModel.jsonDbList }

    var search by remember{ mutableStateOf("") }

    val filteredList by remember {
        derivedStateOf {
            jsonDbList.filter {
                if (search.isEmpty()) return@filter true
                else it.word.contains(search)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 96.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomizedTextField(
            value = search,
            label = "Search",
            leadingIcon = Icons.Outlined.Search, shape = RoundedCornerShape(25)
        ) {
            search = it
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(filteredList, {it:DbObject -> it.objectID}){
                Item(viewModel = viewModel, dbObject = it){ goToWord() }
            }
        }
    }
}