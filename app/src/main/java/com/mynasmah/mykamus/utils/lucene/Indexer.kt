package com.mynasmah.mykamus.utils.lucene

import org.apache.lucene.analysis.Analyzer

class Indexer {
    companion object {

        val analyzer: Analyzer
            get() = object : Analyzer() {
                override fun createComponents(s: String): Analyzer.TokenStreamComponents? {
                    return null
                }
            }
    }

}
