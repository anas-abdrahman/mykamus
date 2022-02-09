package com.mynasmah.mykamus.data

import android.content.Context
import com.mynasmah.mykamus.data.model.Kamus
import com.mynasmah.mykamus.data.model.Suggestion
import com.mynasmah.mykamus.di.annotation.ApplicationContext
import com.mynasmah.mykamus.ui.view.home.FragmentHome
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ANAS on 25/15/17.
 */

@Singleton class DataManager @Inject constructor(@ApplicationContext private val mContext: Context, private val mDatabaseHelper: DatabaseHelper, private val mSharedPrefsHelper: SharedPrefsHelper) {

    fun copyDatabase()
    {
        return mDatabaseHelper.copyDatabase()
    }

    fun getKamus(string : String, language: String) : List<Kamus>
    {
        return mDatabaseHelper.getKamusData(string, language)
    }

    fun getSuggestion(string : String, language: String) : List<Suggestion>
    {
        return mDatabaseHelper.getKamusSuggestion(string, language)
    }


    fun getContex() : Context{
        return mContext
    }
}
