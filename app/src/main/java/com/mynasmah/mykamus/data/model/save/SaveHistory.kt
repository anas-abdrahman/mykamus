package com.mynasmah.mykamus.data.model.save

/**
 * Created by Anas on 27/08/2017.
 */

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

import com.google.gson.Gson
import com.mynasmah.mykamus.data.model.History
import com.mynasmah.mykamus.utils.Constants

import java.util.ArrayList
import java.util.Arrays


class SaveHistory {


    private fun initHistory(context: Context, histories: List<History>) {
        val settings: SharedPreferences
        val editor: Editor

        settings = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        editor = settings.edit()

        val gson = Gson()
        val jsonFavorites = gson.toJson(histories)

        editor.putString(Constants.PREF_HISTORY, jsonFavorites)

        editor.apply()
    }

    fun addHistory(context: Context, history: History) {
        var histories: MutableList<History>? = getHistory(context)
        if (histories == null)
            histories = ArrayList()

        histories.add(history)
        initHistory(context, histories)
    }

    fun removeHistory(context: Context, history: History) {
        val histories = getHistory(context)
        if (histories != null) {
            histories.remove(history)
            initHistory(context, histories)
        }
    }

    fun getHistory(context: Context): ArrayList<History>? {

        val settings: SharedPreferences
        var histories: List<History>

        settings = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

        if (settings.contains(Constants.PREF_HISTORY)) {
            val jsonFavorites = settings.getString(Constants.PREF_HISTORY, null)
            val gson = Gson()
            val favoriteItems = gson.fromJson(jsonFavorites, Array<History>::class.java)

            histories = Arrays.asList(*favoriteItems)
            histories = ArrayList(histories)
        } else
            return null

        return histories
    }
}