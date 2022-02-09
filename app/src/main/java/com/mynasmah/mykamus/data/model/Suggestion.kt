package com.mynasmah.mykamus.data.model

import com.mynasmah.mykamus.utils.searchView.suggestions.model.SearchSuggestion

class Suggestion(var suggestion: String) : SearchSuggestion {

    override fun getBody(): String {
        return suggestion
    }

    fun setBody(body : String){
        suggestion = body
    }

}