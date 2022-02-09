package com.mynasmah.mykamus.ui.view.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.mynasmah.mykamus.R
import com.mynasmah.mykamus.data.DataManager
import org.jetbrains.anko.toast
import javax.inject.Inject
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import io.fabric.sdk.android.Fabric.with


/**
 * Created by ANAS on 2017/02/05.
 */

class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        MobileAds.initialize(this, "ca-app-pub-4843117985594278~1476380545")

       Fabric.with(this, Crashlytics())

    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes) { _, _-> super@MainActivity.onBackPressed()}
                .create()
                .show()
    }

}