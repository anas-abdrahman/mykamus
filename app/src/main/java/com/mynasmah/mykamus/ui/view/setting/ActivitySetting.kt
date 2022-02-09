package com.mynasmah.mykamus.ui.view.setting

import android.app.Activity
import android.os.Bundle
import android.preference.PreferenceFragment

import com.mynasmah.mykamus.R

class ActivitySetting : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()
    }

    class SettingsFragment : PreferenceFragment() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)
        }
    }
}