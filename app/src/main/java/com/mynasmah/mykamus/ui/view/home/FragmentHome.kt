package com.mynasmah.mykamus.ui.view.home


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.mynasmah.mykamus.R
import com.mynasmah.mykamus.data.DataManager
import com.mynasmah.mykamus.data.model.Suggestion
import com.mynasmah.mykamus.ui.adapter.PagerAdapter
import com.mynasmah.mykamus.ui.view.RxSearchObservable
import com.mynasmah.mykamus.ui.view.intro.Intro
import com.mynasmah.mykamus.ui.view.activity.ActivityBookmarks
import com.mynasmah.mykamus.ui.view.activity.ActivityHistory
import com.mynasmah.mykamus.ui.view.setting.ActivitySetting
import com.mynasmah.mykamus.utils.Constants
import com.mynasmah.mykamus.utils.DataSearch
import com.mynasmah.mykamus.utils.LANGUAGE
import com.mynasmah.mykamus.utils.searchView.FloatingSearchView
import com.mynasmah.mykamus.utils.searchView.suggestions.model.SearchSuggestion
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import java.io.File
import java.io.IOException
import javax.inject.Inject

class FragmentHome : Fragment(), DataSearch, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var dataManager: DataManager
    var language: String = LANGUAGE.ARAB.text

    lateinit var et_search: FloatingSearchView
    lateinit var drawer_layout: DrawerLayout
    lateinit var nav_view: NavigationView
    lateinit var tabs: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as MainApplication).component.inject(this)




        initDatabase()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_kamus, container, false)
    }

    private fun setPage(num : Int ) {
        if (viewPager.currentItem != num) {
            viewPager.currentItem = num
            tabs.setScrollPosition(num, 0f, true)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(requestCode == Constants.RESULT_HISTORY) {

            if (resultCode == Activity.RESULT_OK && data != null) {

                val dataSearch     = data.getStringExtra(Constants.EXTRA_HISTORY_TEXT)

                val dataBahasa     = data.getStringExtra(Constants.EXTRA_HISTORY_BAHASA)


                when (dataBahasa){

                    Constants.AR->{
                        setPage(LANGUAGE.ARAB.code)
                        et_search.setSearchText(dataSearch)
                        sendData(dataSearch)
                    }

                    Constants.MY->{
                        setPage(LANGUAGE.MELAYU.code)
                        et_search.setSearchText(dataSearch)
                        sendData(dataSearch)


                    }

                    Constants.EN->{
                        setPage(LANGUAGE.ENGLISH.code)
                        et_search.setSearchText(dataSearch)
                        sendData(dataSearch)

                    }



                }

            }

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mAdView = view.findViewById<AdView>(R.id.adView_home)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        drawer_layout = view.find(R.id.drawer_layout)
        viewPager = view.find(R.id.viewPager)
        et_search = view.find(R.id.et_search)
        nav_view = view.find(R.id.nav_view)
        tabs = view.find(R.id.tabs)

        et_search.setOnMenuItemClickListener {

            when(it.itemId){
                R.id.menu_setting->{
                    setActivity(ActivitySetting::class.java, Constants.RESULT_EMPTY)
                }
                R.id.menu_about->{
                    onCreateDialog()
                }
            }
        }

        initDrawer(); initPager(); initViewListener(); setupSearchBar()
    }

    private fun setupSearchBar() {

        RxSearchObservable.fromView(this)

        et_search.setDismissFocusOnItemSelection(true)

        et_search.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
                if (searchSuggestion != null) {
                    sendData(searchSuggestion.body)
                }
            }

            override fun onSearchAction(currentQuery: String?) {
                if (currentQuery != null) {
                    sendData(currentQuery)
                }
            }
        })

        et_search.setOnBindSuggestionCallback { _, leftIcon, textView, item, _ ->

            val colorSuggestion = item as Suggestion

            leftIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_search_black_24dp, null))

            textView.setTextColor(Color.parseColor("#787878"))
            val text = colorSuggestion.body.replace(et_search.query, "<font color='#0000ff'>" + et_search.query + "</font>", true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
            } else {
                textView.text = Html.fromHtml(text)
            }

        }

        et_search.attachNavigationDrawerToMenuButton(drawer_layout)

    }

    private fun initDrawer() {
        val toggle = ActionBarDrawerToggle(
                activity,
                drawer_layout,
                null,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        nav_view.setNavigationItemSelectedListener(this)

        toggle.syncState()

    }

    private fun initPager() {

        tabs.setupWithViewPager(viewPager)
        setupViewPager(viewPager)

    }

    private fun initViewListener() {

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {

                when (position) {
                    LANGUAGE.ARAB.code -> {
                        language = LANGUAGE.ARAB.text
                        et_search.setSearchHint(getString(R.string.label_ar))
                        et_search.clearQuery()
                    }

                    LANGUAGE.MELAYU.code -> {
                        language = LANGUAGE.MELAYU.text
                        et_search.setSearchHint(getString(R.string.label_my))
                        et_search.clearQuery()
                    }

                    LANGUAGE.ENGLISH.code -> {
                        language = LANGUAGE.ENGLISH.text
                        et_search.setSearchHint(getString(R.string.label_eg))
                        et_search.clearQuery()
                    }
                }

            }

        })

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        when (id) {
            R.id.nav_fav -> setActivity(ActivityBookmarks::class.java, Constants.RESULT_EMPTY)
            R.id.nav_history -> setActivity(ActivityHistory::class.java, Constants.RESULT_HISTORY)
            R.id.nav_setting -> setActivity(ActivitySetting::class.java, Constants.RESULT_EMPTY)
            R.id.nav_about -> onCreateDialog()
            R.id.nav_exit -> (activity)?.onBackPressed()
            R.id.nav_web -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.WEB))
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = PagerAdapter(fragmentManager)
        adapter.addFragment(FragmentKamus.newInstance(LANGUAGE.ARAB.text), LANGUAGE.ARAB.text)
        adapter.addFragment(FragmentKamus.newInstance(LANGUAGE.MELAYU.text), LANGUAGE.MELAYU.text)
        adapter.addFragment(FragmentKamus.newInstance(LANGUAGE.ENGLISH.text), LANGUAGE.ENGLISH.text)
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = adapter
    }

    private fun setFragmentPager(str: String, pager: Int) {
        val dataQuery = fragmentManager!!.findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + pager) as FragmentKamus
        dataQuery.searchKamus(str)
    }

    override fun sendData(str: String) {

        when (viewPager.currentItem) {

            LANGUAGE.ARAB.code// Fragment Arab
            -> setFragmentPager(str, LANGUAGE.ARAB.code)

            LANGUAGE.MELAYU.code // Fragment Melayu
            -> setFragmentPager(str, LANGUAGE.MELAYU.code)

            LANGUAGE.ENGLISH.code // Fragment English
            -> setFragmentPager(str, LANGUAGE.ENGLISH.code)
        }


    }

    fun setActivity(mClass: Class<*>, mResult: Int) {

        val intent = Intent(activity, mClass)

        if (mResult != Constants.RESULT_EMPTY) {
            startActivityForResult(intent, mResult)
        } else {
            startActivity(intent)
        }

    }

    @SuppressLint("InflateParams")
    internal fun onCreateDialog() {

        val inflater = this.layoutInflater

        val builder = AlertDialog.Builder(context!!)

        builder.setView(inflater.inflate(R.layout.dialog_about, null))
                .setTitle(R.string.app_version)
                .setNegativeButton("Close") { dialog, id -> dialog.dismiss() }

        builder.show()

    }

    private fun initDatabase() {

        try {

            val dbName: String = Constants.DB_NAME
            val dbPath: String = context!!.filesDir.parent + "/databases/"

            val isDatabaseExists = File(dbPath, dbName).exists()

            if (!isDatabaseExists) {

                startActivity(Intent(activity, Intro::class.java))

                doAsync {

                    try {
                        dataManager.copyDatabase()
                    } catch (e: IOException) {
                    }

                }

            }

//          System.out.println("size database :::: " + db_size());
//            if (db_size() != 11153408L) {
//
//                if (db_path().delete()) {
//                    println("file Deleted")
//                }
//
//                val intent = Intent(this, Intro::class.java)
//
//                startActivity(intent)
//
//            }
//             private fun db_size(): Long? {
//                  return File(this.DB_PATH, this.DB_NAME).length()
//              }
//
//              private fun db_path(): File {
//                  return File(this.DB_PATH, this.DB_NAME)
//              }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
