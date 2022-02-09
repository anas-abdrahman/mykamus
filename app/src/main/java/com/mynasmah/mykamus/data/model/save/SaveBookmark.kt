package com.mynasmah.mykamus.data.model.save

/**
 * Created by Anas on 27/08/2017.
 */

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

import com.google.gson.Gson
import com.mynasmah.mykamus.data.model.Kamus
import com.mynasmah.mykamus.utils.Constants

import java.util.ArrayList
import java.util.Arrays

class SaveBookmark {

    private fun initBookmark(context: Context, favorites: List<Kamus>) {

        val settings: SharedPreferences
        val editor: Editor

        settings = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        editor = settings.edit()

        val gson = Gson()
        val jsonBookmarks = gson.toJson(favorites)

        editor.putString(Constants.PREF_FAVORITES, jsonBookmarks)

        editor.apply()
    }

    fun addBookmark(context: Context, product: Kamus) {

        var bookmarks: MutableList<Kamus>? = getBookmarks(context)
        if (bookmarks == null) {
            bookmarks = ArrayList()
        }

        bookmarks.add(product)
        initBookmark(context, bookmarks)
    }

    fun removeBookmarks(context: Context, bookmark: Kamus) {
        val bookmarks = getBookmarks(context)
        if (bookmarks != null) {
            bookmarks.remove(bookmark)
            initBookmark(context, bookmarks)
        }
    }

    fun getBookmarks(context: Context): ArrayList<Kamus>? {

        val settings: SharedPreferences
        var bookmarks: List<Kamus>

        settings = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

        if (settings.contains(Constants.PREF_FAVORITES)) {
            val jsonFavorites = settings.getString(Constants.PREF_FAVORITES, null)
            val gson = Gson()
            val favoriteItems = gson.fromJson(jsonFavorites, Array<Kamus>::class.java)

            bookmarks = Arrays.asList(*favoriteItems)
            bookmarks = ArrayList(bookmarks)
        } else
            return null

        return bookmarks
    }
}
