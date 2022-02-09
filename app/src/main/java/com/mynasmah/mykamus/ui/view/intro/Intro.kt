package com.mynasmah.mykamus.ui.view.intro

import android.annotation.SuppressLint
import android.os.Bundle

import com.github.paolorotolo.appintro.AppIntro
import com.mynasmah.mykamus.R
import com.mynasmah.mykamus.data.DataManager
import org.jetbrains.anko.doAsync
import java.io.IOException
import javax.inject.Inject



/**
 * Created by anasa on 12/19/2017.
 */

class Intro : AppIntro() {

    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(IntroSlider.newInstance(R.layout.intro_1))
        addSlide(IntroSlider.newInstance(R.layout.intro_2))
        addSlide(IntroSlider.newInstance(R.layout.intro_3))
        addSlide(IntroSlider.newInstance(R.layout.intro_4))

    }

    override fun onSkipPressed() {
        super.onSkipPressed()
        finish()

    }

    override fun onDonePressed() {
        super.onDonePressed()
        finish()

    }


}
