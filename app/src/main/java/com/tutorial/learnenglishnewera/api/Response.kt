package com.tutorial.learnenglishnewera.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* api response bir json objesi döner. Bildiğimiz gibi json objesi en üstte bir liste yapısı ile gelir.
* listenin içerisindeki her bir sözlük, bir ApiResponse data class'ı ile temsil edilir. data class içerisindeki her bir anahtar,
* sözlük içerisindeki bir anahtara denk gelir. Bu eşleşme anahtarın ve data class içerisindeki özelliğin aynı adı taşıması ile yapılır.
* Eğer data class içerisindeki özellik response'daki sözlüğün anahtarından farklı bir isim taşıyorsa,
* @SerialName("word") val kelime: String ile eşleşme sağlanır.
* Her bir alt sözlük için de yine bir data class tanımlanır.
* */


@Serializable
data class ApiResponse(
    val word: String? = null,
    val phonetic: String? = null,
    val phonetics: List<Phonetic> = emptyList(),
    val meanings: List<Meaning> = emptyList(),
    val license: License? = null,
    val sourceUrls: List<String> = emptyList()
)

@Serializable
data class Phonetic(
    val text:String? = null,
    val audio: String? = null,
    val sourceUrl: String? = null,
    val license: License? = null
)

@Serializable
data class Meaning(
    val partOfSpeech: String? = null,
    val definitions: List<Definition> = emptyList(),
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList()
)

@Serializable
data class Definition(
    val definition: String? = null,
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList(),
    val example: String? = null
)

@Serializable
data class License(
    val name: String? = null,
    val url: String? = null
)
