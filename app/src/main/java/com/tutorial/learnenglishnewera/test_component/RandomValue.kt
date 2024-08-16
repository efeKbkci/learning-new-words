package com.tutorial.learnenglishnewera.test_component

import kotlin.random.Random

// Her zaman bir öncekinden farklı bir değer üretecek

class RandomValue<T>(private val list:List<T>){

    private val usedItems:MutableList<T> = mutableListOf()

    fun random():T?{ // eğer veri tabanında hiç öğe yoksa bu durumda buton disable olacak ve fonksiyon null bir item döndüremeyecek
        // nullable olmasının temel nedeni bazı boş olabilecek listeler ile çalışmam. (exampleSentences, mean)
        if (list.isEmpty()) return null
        if (endOfTheList()) usedItems.clear() // eğer liste bitmişse ve hala değer üretilmek isteniyorsa bu durumda usedItems sıfırlanır
        // TestScreen'de değer üretilmeden önce listenin sonuna gelip gelinmediği kontrol edilir
        var newItem = list.random()
        while (usedItems.contains(newItem)) { newItem = list.random() }
        usedItems.add(newItem)

        return newItem
    }

    fun endOfTheList():Boolean{
        return list.size == usedItems.size
    }

    fun resetUsedList(){
        usedItems.clear()
    }
}