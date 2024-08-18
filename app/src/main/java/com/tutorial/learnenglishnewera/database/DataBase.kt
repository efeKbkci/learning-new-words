package com.tutorial.learnenglishnewera.database

import java.io.File

interface DataBase {

    val fileName:String
    val file:File

    suspend fun <T> dbQuery(block:()->T):T

    suspend fun addItem(dbObject: DbObject)

    suspend fun addItems(objectList: List<DbObject>)

    suspend fun getItem(predicate:(DbObject) -> Boolean):DbObject

    suspend fun getItems():MutableList<DbObject>

    suspend fun updateItem(oldObject: DbObject ,newObject: DbObject)

    suspend fun removeItem(dbObject: DbObject)

    fun controlFile():FileStatus
}