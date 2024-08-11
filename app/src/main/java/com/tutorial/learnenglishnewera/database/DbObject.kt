package com.tutorial.learnenglishnewera.database

import kotlinx.serialization.Serializable

@Serializable
data class DbObject(
    val objectID:Long,
    var word:String,
    var exampleSentences:List<String>,
    var mean:List<String>,
    var phonetic:String,
    var pronunciationPath:String,
    var context:List<String>,
    var imagePath:String,
    var isItLearned:Boolean
)
