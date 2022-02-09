package com.mynasmah.mykamus.data.model

/**
 * Created by ANAS on 2/8/2018.
 */

class History(var id: Int, var text: String?, var type: String?, var bahasa: String?) {

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + id
        return result
    }

    override fun equals(obj: Any?): Boolean {

        if (this === obj)
            return true
        if (obj == null)
            return false
        if (javaClass != obj.javaClass)
            return false

        val other = obj as History?

        return if (other!!.id != 0 && other.text != null && other.type != null && other.bahasa != null) {
            id == other.id && text == other.text && type == other.type && bahasa == other.bahasa
        } else false

    }

    override fun toString(): String {

        if (type != null && bahasa != null)
            return "History [id=$id, text=$text, type=$type, bahasa=$bahasa]"

        if (type != null)
            return "History [id=$id, text=$text, type=$type]"

        return if (bahasa != null) "History [id=$id, text=$text, bahasa=$bahasa]" else "History [id=$id, text=$text]"

    }

}
