package com.tutorial.learnenglishnewera.database

import android.os.Environment
import android.util.Log
import com.tutorial.learnenglishnewera.MyViewModel
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

sealed class FileStatus {
    data object Created: FileStatus()
    data object Existing: FileStatus()
    data class Error(val e:Exception): FileStatus()
}

@Serializable
data class DbObject(
    val objectID:Long,
    var word:String,
    var exampleSentences:List<String>,
    var mean:String,
    var type:String,
    var phonetic:String,
    var pronunciationPath:String,
    var context:String,
    var notes:String,
    var isItLearned:Boolean
)


class DataBaseProcess(private val viewModel: MyViewModel):DataBase{

    override val fileName: String
        get() = "UnknownWords.json"
    override val file: File
        get() = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName)

    init {
        when(val status = controlFile()){
            is FileStatus.Created -> Log.i("myapp","Dosya Oluşturuldu")
            is FileStatus.Error -> Log.e("myapp", "Dosya Oluşturulamadı", status.e)
            is FileStatus.Existing -> {
                Log.i("myapp", "Dosya Mevcut")
                val jsonList = runBlocking { getItems() }
                jsonList.forEach { viewModel.jsonDbList.add(it) }
            }
        }
    }

    override suspend fun <T> dbQuery(block: () -> T):T = runBlocking { block() }

    override suspend fun addItem(dbObject: DbObject) = dbQuery {
        viewModel.jsonDbList.add(0, dbObject)
        val jsonObject = Json.encodeToString(viewModel.jsonDbList)
        file.writeText(jsonObject)
    }

    override suspend fun addItems(objectList: List<DbObject>) = dbQuery{
        viewModel.jsonDbList.addAll(objectList)
        val jsonObject = Json.encodeToString(viewModel.jsonDbList)
        file.writeText(jsonObject)
    }

    override suspend fun getItem(predicate: (DbObject) -> Boolean): DbObject = dbQuery {
        viewModel.jsonDbList.first(predicate)
    }

    override suspend fun getItems(): MutableList<DbObject> {
        val fileContent = file.readText()
        return Json.decodeFromString<MutableList<DbObject>>(fileContent)
    }

    override suspend fun updateItem(oldObject: DbObject, newObject: DbObject) = dbQuery {
        viewModel.jsonDbList.replaceAll { if (it == oldObject) newObject else it}
    }

    override suspend fun removeItem(dbObject: DbObject) = dbQuery {
        viewModel.jsonDbList.remove(dbObject)
    }

    override fun controlFile():FileStatus {
        return try {
            if (!file.exists()){
                file.createNewFile()
                file.writeText("[]")
                FileStatus.Created
            } else FileStatus.Existing
        } catch (e: Exception) {
            FileStatus.Error(e)
        }
    }
}
