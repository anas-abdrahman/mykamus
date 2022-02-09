package com.mynasmah.mykamus.data.model

/**
 * Created by NASMAH on 8/24/2017.
 */
class Kamus {

   var id          : Int       = 0
   var arab_0      : String?   = null
   var arab_1      : String?   = null
   var arab_2      : String?   = null
   var melayu      : String?   = null
   var english     : String?   = null


    override fun hashCode(): Int {

        val prime = 31
        val result = 1

        return prime * result + id
    }

    override fun equals(other: Any?): Boolean {

        if (this === other)
            return true
        if (other == null)
            return false
        if (javaClass != other.javaClass)
            return false

        val check = other as Kamus?

        return id == check!!.id

    }

    override fun toString(): String {
        return "Kamus [id=$id, arab_1=$arab_1, arab_2=$arab_2, melayu=$melayu, english=$english]"
    }
}
