package com.tutorial.learnenglishnewera.word_component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.learnenglishnewera.R
import com.tutorial.learnenglishnewera.reuseables.CustomizedText
import com.tutorial.learnenglishnewera.reuseables.CustomizedTextField

@Composable
fun MultipleInsertion(
    modifier: Modifier=Modifier,
    screenIsDisabled:Boolean,
    label:String,
    value:String,
    onValueChange:(String) -> Unit,
    list: SnapshotStateList<String>
){

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        CustomizedTextField(
            value = value,
            label = label,
            trailingIcon = Icons.Outlined.AddCircleOutline,
            onTrailingIcon = {
                if (value.isNotEmpty() && !list.contains(value)){
                    list.add(0,value)
                    onValueChange("")
                }
            },
            enabled = !screenIsDisabled
        ) {
            onValueChange(it)
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
                            if (!screenIsDisabled) list.remove(it)
                        }
                    )
                }
                Divider()
            }
        }
    }
}