package com.mynasmah.mykamus.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mynasmah.mykamus.R
import com.mynasmah.mykamus.data.model.Kamus
import com.mynasmah.mykamus.data.model.save.SaveBookmark
import com.mynasmah.mykamus.utils.Constants
import com.sackcentury.shinebuttonlib.ShineButton



class KamusAdapter(val context : Context, val kamus : List<Kamus>, val language : String, val layout : Int) : RecyclerView.Adapter<KamusAdapter.Holder>() {

    private lateinit var mOnClick: (kamus : Kamus) -> Unit
    private var saveBookmark = SaveBookmark()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        saveBookmark = SaveBookmark()

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.txt_0.text = kamus[position].id.toString()
        holder.txt_1.text = kamus[position].arab_1
        holder.txt_2.text = kamus[position].arab_2
        holder.txt_3.text = kamus[position].melayu
        holder.txt_4.text = kamus[position].english

        holder.btn_fav.setOnClickListener {

            if (holder.btn_fav.isChecked) {

                holder.btn_fav.isChecked = true

                saveBookmark.addBookmark(context, kamus[position])

                Snackbar.make(holder.btn_fav, "Add to Bookmark!", Snackbar.LENGTH_SHORT).show()


            } else {

                holder.btn_fav.isChecked = false

                saveBookmark.removeBookmarks(context, kamus[position])

                Snackbar.make(holder.btn_fav, "Remove from Bookmark!", Snackbar.LENGTH_SHORT).show()

            }

        }

        holder.btn_fav.isChecked = checkFavoriteItem(kamus[position])

        holder.btn_share.setOnClickListener {

            val arab_1 = holder.txt_1.text as String
            val arab_2 = holder.txt_2.text as String
            val melayu = holder.txt_3.text as String
            val english = holder.txt_4.text as String

            Constants.shareText(context, language, arab_1, arab_2, melayu, english)
        }

        if(language != "favorite") {
            holder.itemView.setOnClickListener { mOnClick(kamus[position]) }
        }
    }


    private fun checkFavoriteItem(checkProduct: Kamus): Boolean {
        var check = false
        val favorites = saveBookmark.getBookmarks(context)
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

    override fun getItemCount(): Int {
        return kamus.size
    }

    infix fun setItemClickMethod(onClick: (kamus : Kamus) -> Unit) {
        this.mOnClick = onClick
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var txt_0 = itemView.findViewById(R.id.txt_0) as TextView
        var txt_1 = itemView.findViewById(R.id.txt_1) as TextView
        var txt_2 = itemView.findViewById(R.id.txt_2) as TextView
        var txt_3 = itemView.findViewById(R.id.txt_3) as TextView
        var txt_4 = itemView.findViewById(R.id.txt_4) as TextView


        var btn_fav = itemView.findViewById(R.id.btn_fav) as ShineButton
        var btn_share = itemView.findViewById(R.id.btn_share) as ShineButton

    }



}