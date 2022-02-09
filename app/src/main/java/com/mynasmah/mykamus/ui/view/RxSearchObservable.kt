package com.mynasmah.mykamus.ui.view

import com.mynasmah.mykamus.ui.view.home.FragmentHome
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


object RxSearchObservable {



    fun fromView(f: FragmentHome) {


        f.et_search.setOnQueryChangeListener { _, newQuery ->

            if(newQuery.isEmpty()){

                f.et_search.swapSuggestions(emptyList())
                f.et_search.hideProgress()

            }else{

                f.et_search.showProgress()
                 Observable.just(newQuery)
                         .debounce(400, TimeUnit.MILLISECONDS)
                         .filter { !it.isEmpty() }
                         .distinctUntilChanged()
                         .switchMap { text ->

                            Observable.fromCallable {
                                f.dataManager.getSuggestion(text, f.language)
                            }

                         }
                         .subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe {
                             f.et_search.swapSuggestions(it)
                             f.et_search.hideProgress()
                         }
            }

        }

    }

}