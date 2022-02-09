package com.mynasmah.mykamus.ui.view.home


import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.mynasmah.mykamus.R
import com.mynasmah.mykamus.data.DataManager
import com.mynasmah.mykamus.data.model.History
import com.mynasmah.mykamus.data.model.save.SaveBookmark
import com.mynasmah.mykamus.data.model.save.SaveHistory
import com.mynasmah.mykamus.ui.adapter.KamusAdapter
import com.mynasmah.mykamus.ui.adapter.SuggestAdapter
import com.mynasmah.mykamus.ui.view.activity.ActivityBookmarks
import com.mynasmah.mykamus.ui.view.activity.ActivityDetails
import com.mynasmah.mykamus.ui.view.activity.ActivityHistory
import com.mynasmah.mykamus.utils.Constants
import com.mynasmah.mykamus.utils.lucene.DictionaryFile
import com.mynasmah.mykamus.utils.lucene.Indexer
import com.mynasmah.mykamus.utils.searchView.FloatingSearchView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.search.spell.SpellChecker
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.lukhnos.portmobile.file.Path
import org.lukhnos.portmobile.file.Paths
import java.io.InputStreamReader
import javax.inject.Inject


/**
 * Created by NASMAH on 8/16/2017.
 */

open class FragmentKamus : Fragment() {

    companion object {
        fun newInstance(language: String): FragmentKamus {

            val bundle = Bundle()
            val fragment = FragmentKamus()

            bundle.putString(Constants.TAG_BAHASA, language)

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as MainApplication).component.inject(this)

        initFragment(); initLanguage()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

            val mSuggestExtra = Constants.EXCEPTION_SUGGEST + "_" + language
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)

            val isSuggest = sharedPreferences.getBoolean(mSuggestExtra, true)

            if(isSuggest) {

                doAsync {

                    sharedPreferences.edit().putBoolean(mSuggestExtra, false).apply()

                    val directory: Directory = FSDirectory.open(getSuggestionIndexPath())

                    val analyzer: Analyzer = Indexer.analyzer
                    val config = IndexWriterConfig(analyzer)
                    val inputStreamReader = InputStreamReader(context!!.assets.open(fileSuggest))
                    val indexDic = DictionaryFile(inputStreamReader)

                    spellChecker = SpellChecker(directory)
                    spellChecker!!.indexDictionary(indexDic, config, false)

                    sharedPreferences.edit().putBoolean(mSuggestExtra, true).apply()

                }
            }
        }
    }

    private fun getSuggestionIndexPath(): Path {

        var mPath = Constants.DIR_SUGGESTION_AR

        when (language) {

            Constants.AR -> mPath = Constants.DIR_SUGGESTION_AR

            Constants.MY -> mPath = Constants.DIR_SUGGESTION_MY

            Constants.EN -> mPath = Constants.DIR_SUGGESTION_EN
        }
        val indexRootPath = Paths.get(activity!!.getDatabasePath(mPath).path)

        indexRootPath.resolve(mPath)

        return indexRootPath
    }

    private fun suggestion(suggest: String): List<String?> {

        var result = arrayOfNulls<String>(0)

        try {

            val suggestions: Array<String?> = spellChecker!!.suggestSimilar(suggest, 15)

            if (suggestions.isNotEmpty()) {

                result = suggestions

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result.toList()

    }

    private fun getSuggestionSearch() {

        val list = suggestion(DataQuery)

        if (list.isNotEmpty()) {

            val suggestAdapter = SuggestAdapter(activity, list)

            listSuggest.adapter = suggestAdapter

            listSuggest.setOnItemClickListener { _, _, position, _ ->

                val textNew = list[position]

                if (textNew != null) {

                    searchKamus(textNew)

                    val etsearch = activity!!.find(R.id.et_search) as FloatingSearchView
                    etsearch.setSearchText(textNew)

                }

            }

        } else {

            layoutSuggestion.visibility = View.GONE

        }
    }


    override fun onStart() {
        super.onStart()

        if (kamusAdapter != null) listResult.adapter = kamusAdapter

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_kamus_home, container, false)
    }

    private fun initFragment() {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        saveBookmark = SaveBookmark()
        saveHistory = SaveHistory()

    }

    private fun onClickListener() {

        txtResult.setOnClickListener { setVisibility(Constants.VISIBLE_DEFAULT) }
        txtQuickHistory.setOnClickListener { startActivity(Intent(activity, ActivityHistory::class.java)) }
        txtQuickBookmark.setOnClickListener { startActivity(Intent(activity, ActivityBookmarks::class.java)) }

    }

    private fun onItemClicked() {

        kamusAdapter?.setItemClickMethod { kamus ->

            val intent = Intent(activity, ActivityDetails::class.java)

            intent.putExtra(Constants.EXTRA_BAHASA, language)
            intent.putExtra(Constants.EXTRA_ERTI_0, kamus.id)
            intent.putExtra(Constants.EXTRA_ERTI_1, kamus.arab_1)
            intent.putExtra(Constants.EXTRA_ERTI_2, kamus.arab_2)
            intent.putExtra(Constants.EXTRA_ERTI_3, kamus.melayu)
            intent.putExtra(Constants.EXTRA_ERTI_4, kamus.english)

            startActivity(intent)

        }

    }



    private fun initLanguage() {

        language = arguments?.getString(Constants.TAG_BAHASA)

        when (language) {

            Constants.AR -> {
                description = activity!!.resources.getText(R.string.bahasa_arab).toString()
                layoutResource = R.layout.listview_kamus_ar
                fileSuggest = Constants.FILE_TXT_ARAB

            }

            Constants.MY -> {
                description = activity!!.resources.getText(R.string.bahasa_melayu).toString()
                layoutResource = R.layout.listview_kamus_my
                fileSuggest = Constants.FILE_TXT_MELAYU

            }

            Constants.EN -> {
                description = activity!!.resources.getText(R.string.bahasa_english).toString()
                layoutResource = R.layout.listview_kamus_en
                fileSuggest = Constants.FILE_TXT_ENGLISH

            }

        }

    }

    private fun setVisibility(config: String) {

        resetView()

        when (config) {

            Constants.VISIBLE_DEFAULT -> {

                layoutDashboard.visibility = View.VISIBLE
                txtLabel.text = description

                txtQuickBookmark.visibility = View.VISIBLE
                txtQuickHistory.visibility = View.VISIBLE
                txtQuestion.visibility = View.VISIBLE

            }

            Constants.VISIBLE_RESULT -> {
                layoutResult.visibility = View.VISIBLE
            }

            Constants.VISIBLE_NORESULT -> {

                layoutNoResult.visibility = View.VISIBLE
                layoutSuggestion.visibility = View.VISIBLE

                if (spellChecker != null) getSuggestionSearch()


            }

            Constants.VISIBLE_SEARCH -> {
                layoutDashboard.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
                txtLabel.setText(R.string.sedang_cari)
            }
        }

    }

    private fun resetView() {

        layoutDashboard.visibility = View.GONE
        layoutResult.visibility = View.GONE
        layoutNoResult.visibility = View.GONE
        layoutSuggestion.visibility = View.GONE

        txtQuickBookmark.visibility = View.GONE
        txtQuickHistory.visibility = View.GONE
        txtQuestion.visibility = View.GONE

        progressBar.visibility = View.GONE

    }

    @SuppressLint("CheckResult")
    fun searchKamus(query: String) {

        DataQuery = query

        Observable.fromCallable {



            dataManager.getKamus(query, language!!)

        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { setVisibility(Constants.VISIBLE_SEARCH) }
                .subscribe { list ->

                    if (list.isEmpty()) {

                        setVisibility(Constants.VISIBLE_NORESULT)

                        Answers.getInstance().logContentView(ContentViewEvent()
                                .putContentName(query)
                                .putContentType(language)
                                .putContentId("not-found")
                        )

                    } else {

                        if(checkHistory(query, true)) {
                            saveHistory.addHistory(context!!, History(query.length, query, Constants.OFFLINE, language))
                        }

                        setVisibility(Constants.VISIBLE_RESULT)

                        txtResult.text = StringBuilder("Total searches (" + list.size + ") : " + query)

                        kamusAdapter = KamusAdapter(context!!, list, language!!, layoutResource)

                        listResult.adapter = kamusAdapter

                        onItemClicked()

                        Answers.getInstance().logContentView(ContentViewEvent()
                                .putContentName(query)
                                .putContentType(language)
                                .putContentId("found")
                        )

                    }

                }

    }

    fun checkHistory(checkHistory : String, deleteHistory : Boolean = false) : Boolean {

        var check = false

        val histories = saveHistory.getHistory(context!!)

        if (histories != null) {
            for (history in histories) {
                if (history.text.equals(checkHistory)) {

                    check = if ( deleteHistory ) {

                        saveHistory.removeHistory(context!!, history)

                        false

                    }else{

                        true

                    }

                    break
                }
            }
        }

        return !check
    }

    @Inject
    lateinit var dataManager: DataManager
    private var description: String = ""
    private var DataQuery: String = ""
    private var language: String? = ""
    private var fileSuggest = ""
    private var layoutResource: Int = 0
    private var spellChecker: SpellChecker? = null


    private lateinit var layoutDashboard: LinearLayout
    private lateinit var layoutResult: LinearLayout
    private lateinit var layoutNoResult: LinearLayout
    private lateinit var layoutSuggestion: LinearLayout

    private lateinit var txtResult: TextView
    private lateinit var txtQuestion: TextView
    private lateinit var txtLabel: TextView
    private lateinit var txtQuickHistory: TextView
    private lateinit var txtQuickBookmark: TextView
    private lateinit var txtNoResult: TextView
    private lateinit var imgLabel: ImageView
    private lateinit var imgNoResult: ImageView

    private lateinit var saveHistory: SaveHistory
    private lateinit var saveBookmark: SaveBookmark
    private lateinit var preferences: SharedPreferences

    private lateinit var progressBar: ProgressBar
    private lateinit var listResult: RecyclerView
    private lateinit var listSuggest: ListView
    private var kamusAdapter: KamusAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutDashboard = view.find(R.id.layout_dashboard)
        layoutResult = view.find(R.id.layout_result)
        layoutNoResult = view.find(R.id.layout_no_result)
        layoutSuggestion = view.find(R.id.layout_suggestion)

        txtLabel = view.find(R.id.txt_utama)
        imgLabel = view.find(R.id.img_utama)

        txtResult = view.find(R.id.txt_result)
        listResult = view.find(R.id.list_result)

        imgNoResult = view.find(R.id.img_noResult)
        txtNoResult = view.find(R.id.txt_noResult)
        txtQuestion = view.find(R.id.txt_question)
        progressBar = view.find(R.id.progressBar)
        listSuggest = view.find(R.id.list_suggest)

        txtQuickHistory = view.find(R.id.txt_quick_history)
        txtQuickBookmark = view.find(R.id.txt_quick_bookmark)

        listResult.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        onClickListener(); setVisibility(Constants.VISIBLE_DEFAULT)

    }

}
