package com.mynasmah.mykamus.ui.view.activity

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import com.google.android.material.snackbar.Snackbar
import com.mynasmah.mykamus.ui.adapter.HistoryAdapter
import com.mynasmah.mykamus.data.model.History
import com.mynasmah.mykamus.R
import com.mynasmah.mykamus.data.model.save.SaveHistory
import kotlinx.android.synthetic.main.activity_history.*


class ActivityHistory : AppCompatActivity() {

    internal lateinit var list_history: ListView
    lateinit var saveKamus: SaveHistory
    internal var historys: MutableList<History>? = null

    lateinit var kamusListAdapter: HistoryAdapter
    internal lateinit var txt_title: TextView
    internal lateinit var txt_remove_all: TextView

    lateinit var noHistory: LinearLayout

    public override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val mAdView = findViewById<AdView>(R.id.adView_history)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        saveKamus = SaveHistory()
        historys = saveKamus.getHistory(this)
        list_history = findViewById(R.id.list_history)
        txt_title = findViewById(R.id.txt_title_history)
        txt_remove_all = findViewById(R.id.txt_remove_all)
        noHistory = findViewById(R.id.no_history)

        if (historys != null && historys!!.size != 0) {

            noHistory.visibility = View.GONE
            txt_remove_all.visibility = View.VISIBLE
            list_history.visibility = View.VISIBLE

            setCountHistory(historys!!.size)
            historys!!.reverse()
            kamusListAdapter = HistoryAdapter(this, historys)
            list_history.adapter = kamusListAdapter

        } else {

            setCountHistory(0)
            txt_remove_all.visibility = View.GONE
            list_history.visibility = View.GONE
            noHistory.visibility = View.VISIBLE

        }

        txt_remove_all.setOnClickListener { v ->

            try {

                if (historys!!.size != 0) {

                    setCountHistory(0)
                    txt_remove_all.visibility = View.GONE
                    list_history.visibility = View.GONE
                    noHistory.visibility = View.VISIBLE

                    Snackbar.make(v, "The History has been successfully removed!", Snackbar.LENGTH_SHORT).show()

                    AsyncTask.execute {
                        try {

                            if (checkFavoriteItem(historys!![0])) {

                                for (i in historys!!.indices.reversed()) {

                                    try {

                                        saveKamus.removeHistory(this, historys!![i])
                                        historys!!.removeAt(i)

                                    } catch (e: Exception) {}

                                }
                            }

                        } catch (e: Exception) { }


                    }

                }

            } catch (e: Exception) { }


        }

    }

    private fun checkFavoriteItem(checkHistory: History?): Boolean {

        var check = false

        if (checkHistory != null) {

            val favorites = saveKamus.getHistory(this)

            if (favorites != null) {

                for (history in favorites) {

                    if (history == checkHistory) {
                        check = true
                        break
                    }
                }
            }
        }

        return check
    }


    fun setCountHistory(count: Int) {

        val textOfFavorite = "Search History ( $count )"

        txt_title_history.text = textOfFavorite
    }

}
