package com.mynasmah.mykamus.ui.view.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import com.google.android.material.snackbar.Snackbar
import com.mynasmah.mykamus.R
import com.mynasmah.mykamus.data.model.Kamus
import com.mynasmah.mykamus.utils.Constants
import com.mynasmah.mykamus.data.model.save.SaveBookmark
import kotlinx.android.synthetic.main.activity_details.*



class ActivityDetails : AppCompatActivity(), View.OnClickListener {

    internal var id: Int = 0
    lateinit var bahasa: String
    lateinit var arab_1: String
    lateinit var arab_2: String
    lateinit var melayu: String
    lateinit var english: String

    lateinit var saveBookmark: SaveBookmark

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        id      = intent.getIntExtra(Constants.EXTRA_ERTI_0, 0)
        bahasa  = intent.getStringExtra(Constants.EXTRA_BAHASA)
        arab_1  = intent.getStringExtra(Constants.EXTRA_ERTI_1)
        arab_2  = intent.getStringExtra(Constants.EXTRA_ERTI_2)
        melayu  = intent.getStringExtra(Constants.EXTRA_ERTI_3)
        english = intent.getStringExtra(Constants.EXTRA_ERTI_4)

        setContentView(R.layout.activity_details)

        val mAdView = findViewById<AdView>(R.id.adView_details)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        val kamus = Kamus()

        kamus.id = id
        kamus.arab_1 = arab_1
        kamus.arab_2 = arab_2
        kamus.melayu = melayu
        kamus.english = english

        saveBookmark = SaveBookmark()

        erti_1.text = arab_1

        if (arab_2 == Constants.EXTRA_EMPTY) {

            erti_2.visibility = View.GONE
            erti_2.text = Constants.EXTRA_EMPTY

        } else {

            erti_2.text = arab_2
        }

        erti_3.text = melayu

        if (english == Constants.EXTRA_EMPTY) {

            label_2.visibility = View.GONE
            erti_4.visibility = View.GONE
            erti_4.text = Constants.EXTRA_EMPTY

        } else {

            erti_4.text = english
        }

        erti_1.setOnClickListener(this)
        erti_2.setOnClickListener(this)
        erti_3.setOnClickListener(this)
        erti_4.setOnClickListener(this)

        iv_back.setOnClickListener { finish() }

        btn_fav.isChecked = checkFavoriteItem(kamus)

        btn_fav.setOnClickListener { view ->



            if (!checkFavoriteItem(kamus)) {

                saveBookmark.addBookmark(applicationContext, kamus)
                Snackbar.make(view, "Add to Bookmark!", Snackbar.LENGTH_SHORT).show()

            } else {

                saveBookmark.removeBookmarks(applicationContext, kamus)
                Snackbar.make(view, "Remove from Bookmark!", Snackbar.LENGTH_SHORT).show()

            }

        }

        btn_share.setOnClickListener {

            Constants.shareText(this, bahasa, arab_1, arab_2, melayu, english)
        }

        iv_font_up.setOnClickListener {

            if(fontSize < 22f) fontSize++

            erti_1.textSize = fontSize + 4
            erti_2.textSize = fontSize
            erti_3.textSize = fontSize
            erti_4.textSize = fontSize

        }

        iv_font_down.setOnClickListener {

            if(fontSize > 10f) fontSize--

            erti_1.textSize = fontSize + 4
            erti_2.textSize = fontSize
            erti_3.textSize = fontSize
            erti_4.textSize = fontSize

        }

    }

    var fontSize = 14f

    override fun onClick(view: View) {

        val id = view.id
        var clipboardText: CharSequence? = null

        when (id) {

            R.id.erti_1 -> clipboardText = erti_1.text.toString()
            R.id.erti_2 -> clipboardText = erti_2.text.toString()
            R.id.erti_3 -> clipboardText = erti_3.text.toString()
            R.id.erti_4 -> clipboardText = erti_4.text.toString()
        }

        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("mykamus", clipboardText)

        clipboard.primaryClip = clip
        com.google.android.material.snackbar.Snackbar.make(view, "Copied to Clipboard!", Snackbar.LENGTH_SHORT).show()
    }

    private fun checkFavoriteItem(checkProduct: Kamus): Boolean {
        var check = false
        val favorites = saveBookmark.getBookmarks(applicationContext)
        if (favorites != null) {
            for (product in favorites) {
                if (product == checkProduct) {
                    check = true
                    break
                }
            }
        }
        return check
    }

}
