package com.mynasmah.mykamus.ui.view.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crashlytics.android.Crashlytics
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import com.mynasmah.mykamus.R
import com.mynasmah.mykamus.data.model.Kamus
import com.mynasmah.mykamus.data.model.save.SaveBookmark
import com.mynasmah.mykamus.ui.adapter.KamusAdapter
import kotlinx.android.synthetic.main.activity_bookmark.*
import java.util.*

class ActivityBookmarks : AppCompatActivity() {

    internal lateinit var saveBookmark: SaveBookmark
    internal var favorites: List<Kamus>? = null

    internal lateinit var activity: Activity
    internal var kamusAdapter: KamusAdapter? = null

    public override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        val crashButton = Button(this)
        crashButton.text = "Crash!"
        crashButton.setOnClickListener {
            Crashlytics.getInstance().crash() // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT))

       /* val mAdView = findViewById<AdView>(R.id.adView_bookmark)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        activity = this
        saveBookmark = SaveBookmark()
        favorites = saveBookmark.getBookmarks(activity)
        list_fav.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)


        if (favorites != null && favorites!!.isNotEmpty()) {

            no_bookmarks.visibility = View.GONE
            list_fav!!.visibility = View.VISIBLE



            setCountBookmark(favorites!!.size)

            favorites?.reversed()

            kamusAdapter = KamusAdapter(activity, (favorites as ArrayList<Kamus>).toList(), "favorite",R.layout.listview_kamus_favourite)

            list_fav!!.adapter = kamusAdapter


        } else {

            txt_fav.text = getString(R.string.bookmarks)
            no_bookmarks.visibility = View.VISIBLE
            list_fav!!.visibility = View.GONE

        }

        onItemClicked()*/
    }

    fun onItemClicked() {

//        list_fav!!.setOnItemClickListener { parent, view, position, id ->
//
//            val erti_0 = (view.findViewById<View>(R.id.txt_0) as TextView).text.toString()
//            val erti_1 = (view.findViewById<View>(R.id.txt_1) as TextView).text.toString()
//            val erti_2 = (view.findViewById<View>(R.id.txt_2) as TextView).text.toString()
//            val erti_3 = (view.findViewById<View>(R.id.txt_3) as TextView).text.toString()
//            val erti_4 = (view.findViewById<View>(R.id.txt_4) as TextView).text.toString()
//            val btn_fav= (view.findViewById<View>(R.id.btn_fav) as ShineButton)
//
//            val intent = Intent(this, ActivityDetails::class.java)
//
//            intent.putExtra(Constants.EXTRA_BAHASA, Constants.AR)
//            intent.putExtra(Constants.EXTRA_ERTI_0, Integer.parseInt(erti_0))
//            intent.putExtra(Constants.EXTRA_ERTI_1, erti_1)
//            intent.putExtra(Constants.EXTRA_ERTI_2, erti_2)
//            intent.putExtra(Constants.EXTRA_ERTI_3, erti_3)
//            intent.putExtra(Constants.EXTRA_ERTI_4, erti_4)
//
//            if (btn_fav.isChecked)
//                intent.putExtra(Constants.EXTRA_ERTI_5, Constants.CODE_CHECKED)
//            else
//                intent.putExtra(Constants.EXTRA_ERTI_5, Constants.CODE_UNCHECKED)
//
//            startActivity(intent)
//
//        }

    }

    public override fun onStart() {
        super.onStart()

        if (list_fav != null && kamusAdapter != null) {
            list_fav!!.adapter = kamusAdapter
        }
    }

    fun setCountBookmark(count: Int) {

        val textOfFavorite = "Bookmarks ( $count )"

        txt_fav.text = textOfFavorite
    }


}
