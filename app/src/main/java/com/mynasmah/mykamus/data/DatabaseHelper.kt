package com.mynasmah.mykamus.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.preference.PreferenceManager
import com.mynasmah.mykamus.data.model.Kamus
import com.mynasmah.mykamus.data.model.Suggestion
import com.mynasmah.mykamus.di.annotation.ApplicationContext
import com.mynasmah.mykamus.di.annotation.DatabaseInfo
import com.mynasmah.mykamus.utils.Constants
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

/**
 * Created by ANAS on 29/01/19.
 */

@Singleton
class DatabaseHelper
@Inject constructor(
        @ApplicationContext val context: Context,
        @DatabaseInfo val dbName: String,
        @DatabaseInfo val version: Int?) : SQLiteOpenHelper(context, dbName, null, version!!) {

    private val databaseFile: String = context.getDatabasePath(Constants.DB_NAME).path

    fun getKamusData(word: String, language: String): List<Kamus> {

        when (language) {

            Constants.AR -> {

                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                val isSarafEnable = sharedPreferences.getBoolean(Constants.SARAF_ENABLE, true)

                return queryKamus(QueryUtils.arabQuery(word, isSarafEnable), word)

            }
            Constants.MY -> return queryKamus(QueryUtils.melayuQuery(word), word)
            Constants.EN -> return queryKamus(QueryUtils.englishQuery(word), word)

        }

        return emptyList()
    }


    fun getKamusSuggestion(text: String, language: String): List<Suggestion> {

        when (language) {

            Constants.AR -> return querySuggestion(text, "arab_cari")
            Constants.MY -> return querySuggestion(text, "melayu")
            Constants.EN -> return querySuggestion(text, "english")

        }

        return emptyList()
    }

    fun copyDatabase() {

        val localInputStream = context.assets.open(Constants.DB_NAME)

        val localFileOutputStream = FileOutputStream(databaseFile)

        val arrayOfByte = ByteArray(1024)

        while (true) {
            val i = localInputStream.read(arrayOfByte)
            if (i <= 0) {
                localFileOutputStream.flush()
                localFileOutputStream.close()
                localInputStream.close()
                return
            }
            localFileOutputStream.write(arrayOfByte, 0, i)
        }
    }

    private fun querySuggestion(text: String, language: String): List<Suggestion> {

        val final = mutableListOf<Suggestion>()
        val list1 = mutableListOf<Suggestion>()
        val list2 = mutableListOf<Suggestion>()

        val limit = when {
            text.length == 1 -> return final
            text.length == 2 -> " LIMIT 20 "
            else -> " LIMIT 30 "
        }

        val selectQuery1 = QueryUtils.suggestionQuery(text, language, limit)
        val selectQuery2 = "SELECT $language FROM Kamus WHERE $language LIKE '%$text%' $limit"

        try {

            val database1 = SQLiteDatabase.openDatabase(databaseFile, null, 0)
            val database2 = SQLiteDatabase.openDatabase(databaseFile, null, 0)

            val cursor1 = database1.rawQuery(selectQuery1, null)
            val cursor2 = database2.rawQuery(selectQuery2, null)

            if (cursor1.moveToFirst()) {

                if (cursor1 != null) {

                    do {
                        list1.add(Suggestion(cursor1.getString(0)))
                    } while (cursor1.moveToNext())

                    list1.forEach {

                        val splitSuggestion = it.body.split("\\s+".toRegex())
                        val splitQuery = text.split("\\s+".toRegex())

                        it.apply {
                            if (splitSuggestion.size > 2) { // ada 2 query ke atas 'TEXT TEXT...'

                                for (i in 0 until splitQuery.size) {

                                    if (i == 0) {
                                        it.body = splitSuggestion[i]
                                    } else {
                                        it.body += " " + splitSuggestion[i]
                                    }

                                }

                            } else {
                                it.body = splitSuggestion[0]
                            }
                        }


                        it.apply {
                            it.suggestion = ubahReg(it.suggestion)
                        }
                    }
                }
            }

            if (cursor2.moveToFirst()) {

                if (cursor2 != null) {

                    do {
                        list2.add(Suggestion(cursor2.getString(0)))
                    } while (cursor2.moveToNext())

                    list2.forEach {
                        it.suggestion = it.suggestion.getFirst(text)

                        it.apply {
                            it.suggestion = ubahReg(it.suggestion)
                        }
                    }
                }
            }

            cursor1.close()
            cursor2.close()

            close()

            list1.forEach {
                final.add(it)
            }

            list2.forEach {
                final.add(it)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return removeDuplicates(final)

    }

    private fun String.getFirst(text: String): String {

        var contentsString = ""
        val split = split(" ")

        split.forEach { if (it.contains(text))  contentsString = it }

        return if (contentsString.contains(text)) { contentsString } else { "" }

    }

    fun ubahUncode(paramString: String): String {

        var strChar = ""

        for (i in 0 until paramString.length) {

            val code = paramString[i]

            strChar += if (code == ':' || code == '-' || code == ',') {
                ""
            } else {
                code
            }
        }

        return strChar
    }

    fun ubahReg(paramString: String): String {

        return when {

            paramString.contains(".") -> paramString.substringBefore(".")
            paramString.contains(",") -> paramString.substringBefore(",")
            paramString.contains("،") -> paramString.substringBefore("،")
            paramString.contains("-") -> paramString.substringBefore("-")
            paramString.contains("=") -> paramString.substringBefore("=")
            paramString.contains("|") -> paramString.substringBefore("|")
            paramString.contains("+") -> paramString.substringBefore("+")
            paramString.contains(":") -> paramString.substringBefore(":")
            paramString.contains(";") -> paramString.substringBefore(";")
            paramString.contains("/") -> paramString.substringBefore("/")
            paramString.contains("(") -> paramString.substringBefore("(")
            paramString.contains(")") -> paramString.substringBefore(")")
            paramString.contains("[") -> paramString.substringBefore("[")
            paramString.contains("]") -> paramString.substringBefore("]")
            paramString.contains("{") -> paramString.substringBefore("{")
            paramString.contains("}") -> paramString.substringBefore("}")
            paramString.contains("<") -> paramString.substringBefore("<")
            paramString.contains(">") -> paramString.substringBefore(">")

            else -> paramString
        }

    }

    private fun queryKamus(column: String, word: String): List<Kamus> {

        val limit = if (word.length > 2) " LIMIT 30 " else " LIMIT 10 "
        val query = "SELECT * FROM Kamus WHERE $column $limit"
        val kamusList = mutableListOf<Kamus>()

        try {

            val database = SQLiteDatabase.openDatabase(databaseFile, null, 0)

            val cursor = database!!.rawQuery(query, null)

            if (cursor.moveToFirst()) {

                do {

                    val kamus = Kamus()

                    kamus.id = cursor.getInt(0)

                    kamus.arab_0 = cursor.getString(1)

                    kamus.arab_1 = cursor.getString(2)

                    if (cursor.getString(3).isEmpty())
                        kamus.arab_2 = " -- "
                    else
                        kamus.arab_2 = cursor.getString(3)

                    kamus.melayu = cursor.getString(4)

                    if (cursor.getString(5).isEmpty())
                        kamus.english = " -- "
                    else
                        kamus.english = cursor.getString(5)

                    kamusList.add(kamus)

                } while (cursor.moveToNext())

            }

            cursor.close()

            close()

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return kamusList
    }

    private fun removeDuplicates(duplicates: MutableList<Suggestion>): MutableList<Suggestion> {

        val listDuplicate = mutableListOf<String>()
        val listClear = mutableListOf<Suggestion>()

        duplicates.forEach {

            if (!listDuplicate.contains(it.body)) {
                listDuplicate.add(it.body)
            }

        }

        listDuplicate.forEach {
            if (it != "") listClear.add(Suggestion(it))
        }

        listClear.sortBy {
            it.suggestion.length
        }

        return listClear
    }

    override fun onCreate(p0: SQLiteDatabase?) {}

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

}
