package com.mynasmah.mykamus.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.mynasmah.mykamus.di.annotation.ApplicationContext
import com.mynasmah.mykamus.di.annotation.DatabaseInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by anas on 28/01/19.
 */

@Module class AppModule(private val mApplication: Application) {

    @Provides @Singleton @ApplicationContext
    fun provideContext(): Context = mApplication

    @Provides @Singleton fun provideApplication(): Application = mApplication

    @Provides @DatabaseInfo
    fun provideDatabaseName(): String = "mykamus_6_0.db"

    @Provides @DatabaseInfo
    fun provideDatabaseVersion(): Int = 2

    @Provides internal fun provideSharedPrefs(): SharedPreferences {
        return mApplication.getSharedPreferences("demo-prefs", Context.MODE_PRIVATE)
    }

}
