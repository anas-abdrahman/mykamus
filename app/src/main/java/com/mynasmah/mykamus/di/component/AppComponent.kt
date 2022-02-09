package com.mynasmah.mykamus.di.component

import android.app.Application
import android.content.Context
import com.mynasmah.mykamus.data.DataManager
import com.mynasmah.mykamus.data.DatabaseHelper
import com.mynasmah.mykamus.data.SharedPrefsHelper
import com.mynasmah.mykamus.di.annotation.ApplicationContext
import com.mynasmah.mykamus.di.module.AppModule
import com.mynasmah.mykamus.ui.view.home.FragmentHome
import com.mynasmah.mykamus.ui.view.home.FragmentKamus
import dagger.Component
import javax.inject.Singleton


/**
 * Created by anas on 28/01/19.
 */

@Singleton @Component(modules = [AppModule::class])
interface AppComponent {

    /**
     *  target untuk activity atau fragment
     *  boleh tambah target
     *
     * */

    fun inject(target: FragmentKamus)

    fun inject(target: FragmentHome)

    /**
     *  perlu letak get di awal method
     *  supaya DI boleh inject object
     *
     * */

    @ApplicationContext
    fun getContext(): Context

    fun getApplication(): Application

    fun getDataManager(): DataManager

    fun getPreference(): SharedPrefsHelper

    fun getDbHelper(): DatabaseHelper

}
