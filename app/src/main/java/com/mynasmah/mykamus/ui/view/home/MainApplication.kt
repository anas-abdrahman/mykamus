package com.mynasmah.mykamus.ui.view.home

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.mynasmah.mykamus.di.component.AppComponent
import com.mynasmah.mykamus.di.component.DaggerAppComponent
import com.mynasmah.mykamus.di.module.AppModule


class MainApplication : Application()
{

    lateinit var component : AppComponent

    override fun onCreate() {
        super.onCreate()

        component = initDagger(this)

    }

    private fun initDagger(app: MainApplication): AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .build()
}
