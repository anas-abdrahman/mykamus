package com.mynasmah.mykamus.utils

import android.content.Context
import android.content.Intent

/**
 * Created by ANAS on 2017/02/05.
 */


class Constants {

    companion object {

        const val TAG = "com.mynasmah.mykamus"

        const val OFFLINE = "Offline"

        const val RESULT_EMPTY: Int = 1000
        const val RESULT_HISTORY: Int = 1011

        const val CODE_CHECKED = "checked"
        const val CODE_UNCHECKED = "unchecked"

        const val EXCEPTION_SUGGEST = "exception_suggestion"
        const val EXTRA_HISTORY_TEXT = "extra_history_text"
        const val EXTRA_HISTORY_BAHASA = "extra_history_bahasa"

        const val EXTRA_BAHASA = "erti_bahasa"
        const val EXTRA_ERTI_0 = "erti_0"
        const val EXTRA_ERTI_1 = "erti_1"
        const val EXTRA_ERTI_2 = "erti_2"
        const val EXTRA_ERTI_3 = "erti_3"
        const val EXTRA_ERTI_4 = "erti_4"
        const val EXTRA_ERTI_5 = "erti_5"
        const val EXTRA_EMPTY = " -- "

        const val AR = "arab"
        const val MY = "melayu"
        const val EN = "english"
        const val FAV = "favorite"

        const val PAGE_AR = 0
        const val PAGE_MY = 1
        const val PAGE_EN = 2

        const val DIR_SUGGESTION_AR = "suggestion_ar"
        const val DIR_SUGGESTION_MY = "suggestion_my"
        const val DIR_SUGGESTION_EN = "suggestion_en"

        const val FILE_TXT_ARAB = "perkataan_arab.txt"
        const val FILE_TXT_MELAYU = "perkataan_melayu.txt"
        const val FILE_TXT_ENGLISH = "perkataan_english.txt"

        const val DB_NAME = "mykamus_6_0.db"
        const val PREF_NAME = "KAMUS_APP"
        const val PREF_FAVORITES = "Kamus_Favorite"
        const val PREF_HISTORY = "Kamus_History"
        const val PREF_FONT_SIZE = "font_size"
        const val SARAF_ENABLE = "saraf_enable"
        const val TAG_BAHASA = "bahasa"

        const val WEB = "http://mynasmah.com/mykamus/"
        const val API_KEY = "AIzaSyDqTxvZeqOH96RMN5JM1P7LwSdncn1Szus"
        const val APP_RETURN = "jn@$439ijfsof903j3kse"

        const val VISIBLE_RESULT = "visible_result"
        const val VISIBLE_NORESULT = "visible_noresult"
        const val VISIBLE_DEFAULT = "visible_clear"
        const val VISIBLE_SEARCH = "visible_search"

        const val VISIBLE_RESULT_ONLINE = "visible_result_online"
        const val VISIBLE_NORESULT_ONLINE = "visible_noresult_online"

        fun voiddd() : Boolean{
            return true
        }

        fun shareText(context: Context, lang: String, arab_1: String, arab_2: String, melayu: String, english: String) {


            var getText = ""


            when (lang) {

                "arab" -> {

                    getText = if (arab_2 != " -- " && arab_2.isNotEmpty())
                        "$arab_1 : $arab_2\n-----------\n$melayu\n"
                    else
                        "$arab_1\n-----------\n$melayu\n"

                    if (english != " -- " && english.isNotEmpty())
                        getText += "-----------\n$english\n"

                }

                "melayu" -> {

                    getText = if (melayu != " -- " && melayu.isNotEmpty())
                        "$melayu\n-----------\n$arab_1 : $arab_2\n"
                    else
                        "$melayu\n-----------\n$arab_1\n"

                    if (english != " -- " && english.isNotEmpty())
                        getText += "-----------\n$english\n"
                }

                "english" -> {

                    getText = if (english != " -- " && english.isNotEmpty())
                        "$english\n-----------\n$arab_1 : $arab_2\n-----------\n$melayu\n"
                    else
                        "$english\n-----------\n$arab_1\n-----------\n$melayu\n"
                }

                "favorite" -> {

                    getText = if (arab_2 != " -- " && arab_2.isNotEmpty())
                        "$arab_1 : $arab_2\n-----------\n$melayu\n"
                    else
                        "$arab_1\n-----------\n$melayu\n"

                    if (english != " -- " && english.isNotEmpty())
                        getText += "-----------\n$english\n"
                }
            }

            getText += "\n[Share From mykamus]"


            val sharingIntent = Intent(Intent.ACTION_SEND)

            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(Intent.EXTRA_TEXT, getText)
            context.startActivity(Intent.createChooser(sharingIntent, "Share Using : "))

        }
    }





}


enum class LANGUAGE(val text: String, val code: Int) {
    ARAB(Constants.AR, Constants.PAGE_AR),
    MELAYU(Constants.MY, Constants.PAGE_MY),
    ENGLISH(Constants.EN, Constants.PAGE_EN)
}


