package com.mynasmah.mykamus.data


/**
 * Created by NASMAH on 8/15/2017.
 */

object QueryUtils {


    var key = "8952jh35h892132f690904"

    /**
     * KAMUS QUERY
     * */


    fun suggestionQuery(text: String, language: String, limit: String): String {

        val strAlifLam: String
        var strAlifLam1 = ""

        var strHamzah = ""
        val strHamzahFa: String
        val strHamzahKa: String
        val strHamzahMa: String

        var strHamzahx = ""
        val strHamzahFax: String
        val strHamzahKax: String
        val strHamzahMax: String

        if (QueryUtils.isAlifLam(text)!!) {

            strHamzahFa = QueryUtils.ubahHamzahFa(QueryUtils.ubahAlifLam(text))
            strHamzahKa = QueryUtils.ubahHamzahKa(QueryUtils.ubahAlifLam(text))
            strHamzahMa = QueryUtils.ubahHamzahMa(QueryUtils.ubahAlifLam(text))

            strHamzah = " OR arab_cari LIKE '$strHamzahFa%' OR arab_cari LIKE '$strHamzahKa%'  OR arab_cari LIKE '$strHamzahMa%' "

            strAlifLam = QueryUtils.ubahAlifLam(text)
            strAlifLam1 = " OR arab_cari LIKE '$strAlifLam' OR arab_cari LIKE '$strAlifLam ' "


        } else if (QueryUtils.isAlif(text)!!) {

            strHamzahFa = QueryUtils.ubahHamzahFa(text)
            strHamzahKa = QueryUtils.ubahHamzahKa(text)
            strHamzahMa = QueryUtils.ubahHamzahMa(text)

            strHamzah = " OR arab_cari LIKE '$strHamzahFa%' OR arab_cari LIKE '$strHamzahKa%'  OR arab_cari LIKE '$strHamzahMa%' "

        }

        if (QueryUtils.isAlifSahaja(text)!!) {

            strHamzahFax = QueryUtils.ubahHamzahFa(text)
            strHamzahKax = QueryUtils.ubahHamzahKa(text)
            strHamzahMax = QueryUtils.ubahHamzahMa(text)

            strHamzahx = "  OR arab_cari LIKE '$strHamzahFax%' OR arab_cari LIKE '$strHamzahKax%'  OR arab_cari LIKE '$strHamzahMax%' "
        }

        return "SELECT $language FROM Kamus WHERE $language LIKE '$text%'  $strAlifLam1 $strHamzah $strHamzahx $limit"

    }

    fun arabQuery(word: String, isSarafEnable: Boolean): String {

        var str = word

        var yaMadlastQuery: String = ""
        val strYaMadLast: String

        var MadlastQuery = ""
        val strMadLast: String

        val strSaraf: String?
        var sarafQuery = ""

        val strJama: String
        var jamaQuery = ""

        val strTanisLast: String
        var tanisLastQuery = ""

        val strMudhari: String
        var mudhariQuery = ""

        val strAlifLam: String
        var alifLamQuery = ""
        var strAlifLam1C = " arab_cari LIKE '' "

        var hamzahHarakatQuery = ""
        var strHamzahC1 = " arab_cari LIKE '' "
        var strHamzahC2 = " arab_cari LIKE '' "
        var strHamzahC3 = " arab_cari LIKE '' "
        val strHamzahFa: String
        val strHamzahKa: String
        val strHamzahMa: String

        var hamzahQuery = ""
        var strHamzahC1x = " arab_cari LIKE '' "
        var strHamzahC2x = " arab_cari LIKE '' "
        var strHamzahC3x = " arab_cari LIKE '' "
        val strHamzahFax: String
        val strHamzahKax: String
        val strHamzahMax: String

        try {

//            StringBuilder querySearch = new StringBuilder();
//            String[] splitString = str.split(" ");
//            for(String value: splitString){
//                String mString = " OR arab_cari LIKE '"+value+"%' ";
//                querySearch.append(mString);
//            }

            // string Space First
            if (QueryUtils.isUncode(str)) {
                str = QueryUtils.ubahUncode(str)
            }

            // string Space First
            if (QueryUtils.isSpaceFirst(str)) {
                str = QueryUtils.ubahSpaceFirst(str)
            }

            // string Space Last
            if (QueryUtils.isSpaceLast(str)) {
                str = QueryUtils.ubahSpaceLast(str)
            }

            if (isSarafEnable) {

                // string Ta Tanis
                if (QueryUtils.isLastTanis(str)!!) {
                    strTanisLast = QueryUtils.ubahTanisLast(str)
                    tanisLastQuery = " OR arab_cari LIKE '$strTanisLast%' "
                }

                // string Mad Last
                if (QueryUtils.isMadLast(str)!!) {
                    strMadLast = QueryUtils.ubahLast(str)
                    MadlastQuery = " OR arab_cari LIKE '$strMadLast%' "
                }

                // string Ya Mad Last
                if (QueryUtils.isLastYaMad(str)!!) {
                    strYaMadLast = QueryUtils.ubahYaMadLast(str)
                    yaMadlastQuery = " OR arab_cari LIKE '$strYaMadLast%' "
                }

                // string Jama Last
                if (QueryUtils.isJamaLast(str)) {
                    strJama = QueryUtils.ubahJamaLast(str)
                    jamaQuery = " OR arab_cari LIKE '$strJama' OR arab_cari LIKE '$strJama%' OR arab_cari LIKE '%$strJama' "
                }

                // string Mudhari
                if (QueryUtils.isMudhari(str)) {
                    strMudhari = QueryUtils.ubahMudhari(str)
                    mudhariQuery = " OR arab_cari LIKE '$strMudhari'  OR arab_cari LIKE '$strMudhari %'  OR arab_cari LIKE '% $strMudhari' "
                }


                // string Saraf
                if (QueryUtils.checkSaraf(str) != null) {
                    if (str.length > 3) {
                        strSaraf = QueryUtils.checkSaraf(str)
                        sarafQuery = " OR arab_cari LIKE '$strSaraf' OR arab_cari LIKE '$strSaraf%' OR arab_cari LIKE '%$strSaraf' "
                    }
                }


                // string Alif Lam
                if (QueryUtils.isAlifLam(str)!!) {
                    strHamzahFa = QueryUtils.ubahHamzahFa(QueryUtils.ubahAlifLam(str))
                    strHamzahKa = QueryUtils.ubahHamzahKa(QueryUtils.ubahAlifLam(str))
                    strHamzahMa = QueryUtils.ubahHamzahMa(QueryUtils.ubahAlifLam(str))

                    hamzahHarakatQuery = " OR arab_cari LIKE '$strHamzahFa%' OR arab_cari LIKE '$strHamzahKa%'  OR arab_cari LIKE '$strHamzahMa%' "

                    strHamzahC1 = " arab_cari LIKE '$strHamzahFa' "
                    strHamzahC2 = " arab_cari LIKE '$strHamzahKa' "
                    strHamzahC3 = " arab_cari LIKE '$strHamzahMa' "

                    strAlifLam = QueryUtils.ubahAlifLam(str)

                    alifLamQuery = " OR arab_cari LIKE '$strAlifLam' OR arab_cari LIKE '$strAlifLam ' "
                    strAlifLam1C = " arab_cari LIKE '$strAlifLam' "

                } else if (QueryUtils.isAlif(str)!!) {

                    strHamzahFa = QueryUtils.ubahHamzahFa(str)
                    strHamzahKa = QueryUtils.ubahHamzahKa(str)
                    strHamzahMa = QueryUtils.ubahHamzahMa(str)

                    hamzahHarakatQuery = " OR arab_cari LIKE '$strHamzahFa%' OR arab_cari LIKE '$strHamzahKa%'  OR arab_cari LIKE '$strHamzahMa%' "
                    strHamzahC1 = " arab_cari LIKE '$strHamzahFa' OR arab_cari LIKE '$strHamzahFa %' "
                    strHamzahC2 = " arab_cari LIKE '$strHamzahKa' OR arab_cari LIKE '$strHamzahKa %' "
                    strHamzahC3 = " arab_cari LIKE '$strHamzahMa' OR arab_cari LIKE '$strHamzahMa %' "

                }

                if (QueryUtils.isAlifSahaja(str)!!) {

                    strHamzahFax = QueryUtils.ubahHamzahFa(str)
                    strHamzahKax = QueryUtils.ubahHamzahKa(str)
                    strHamzahMax = QueryUtils.ubahHamzahMa(str)

                    hamzahQuery = "  OR arab_cari LIKE '$strHamzahFax%' OR arab_cari LIKE '$strHamzahKax%'  OR arab_cari LIKE '$strHamzahMax%' "
                    strHamzahC1x = "  arab_cari LIKE '$strHamzahFax' OR arab_cari LIKE '$strHamzahFax %' "
                    strHamzahC2x = "  arab_cari LIKE '$strHamzahKax' OR arab_cari LIKE '$strHamzahKax %' "
                    strHamzahC3x = "  arab_cari LIKE '$strHamzahMax' OR arab_cari LIKE '$strHamzahMax %' "

                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


        return (" arab_cari LIKE '%$str%' "

                + tanisLastQuery
                + MadlastQuery
                + yaMadlastQuery
                + jamaQuery
                + mudhariQuery
                + sarafQuery
                + alifLamQuery
                + hamzahHarakatQuery
                + hamzahQuery

                + " ORDER BY ("

                + "CASE WHEN arab_cari LIKE '" + str + "' "

                + "THEN 1 WHEN arab_cari LIKE '" + str + "' "
                + "OR arab_cari LIKE '" + str + " ' "
                + "OR arab_cari LIKE ' " + str + "' "
                + "OR arab_cari LIKE ' " + str + " ' "

                + "THEN 2 WHEN arab_cari LIKE '" + str + "%[جمع] ' "
                + "OR arab_cari LIKE '" + str + " ج:%' "
                + "OR arab_cari LIKE '%" + str + "%[جمع] ' "
                + "OR arab_cari LIKE '" + str + " -%' "
                + "OR arab_cari LIKE '%- " + str + "' "
                + "OR arab_cari LIKE '" + str + ")%'  "
                + "OR arab_cari LIKE '" + str + "(%'  "
                + "OR arab_cari LIKE '" + str + " )%' "
                + "OR arab_cari LIKE '" + str + " (%' "

                + "THEN 3 WHEN  arab_cari LIKE '" + str + " %' "
                + "OR arab_cari LIKE '" + str + "% لـ ' "
                + "OR arab_cari LIKE '" + str + "% بـ ' "
                + "OR arab_cari LIKE '" + str + "% على ' "
                + "OR arab_cari LIKE '" + str + "% في ' "

                + "THEN 4 WHEN arab_cari LIKE '% " + str + "' "
                + "OR arab_cari LIKE '% " + str + " %' "

                + "THEN 5 WHEN " + strHamzahC1x + " "
                + "OR " + strHamzahC1 + " "

                + "THEN 6 WHEN " + strHamzahC2x + " "
                + "OR " + strHamzahC2 + " "

                + "THEN 7 WHEN " + strHamzahC3x + " "
                + "OR " + strHamzahC3 + " "

                + "THEN 8 WHEN  " + strAlifLam1C + " "

                + "THEN 9 WHEN arab_cari LIKE '" + str + " %' " + yaMadlastQuery + tanisLastQuery + "  "

                + "THEN 10 WHEN arab_cari LIKE '" + str + "%' " + mudhariQuery + " "

                + "THEN 11 ELSE 12 END ) "

                )
    }

    fun melayuQuery(str: String): String {
        var value = str

        try {

            // string Space First
            if (QueryUtils.isUncode(value)) {
                value = QueryUtils.ubahUncode(value)
            }

            // string Space First
            if (QueryUtils.isSpaceFirst(value)) {
                value = QueryUtils.ubahSpaceFirst(value)
            }

            // string Space Last
            if (QueryUtils.isSpaceLast(value)) {
                value = QueryUtils.ubahSpaceLast(value)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


        return " melayu LIKE  '%" + value + "%' ORDER BY (" +
                "CASE WHEN melayu = '" + value + "' " +
                "THEN 1 WHEN melayu LIKE '" + value + ",%' " +
                "OR melayu LIKE '" + value + " ,%' " +
                "THEN 2 WHEN  melayu LIKE '%," + value + "' " +
                "THEN 3 WHEN melayu LIKE '%," + value + ",%' " +
                "OR melayu LIKE '%," + value + " ,%' " +
                "OR melayu LIKE '%, " + value + ",%' " +
                "OR melayu LIKE '%, " + value + " ,%' " +
                "THEN 4 WHEN melayu LIKE '" + value + " %' " +
                "THEN 5 WHEN melayu LIKE '" + value + "%'  " +
                "THEN 6 ELSE 7 END)"

    }

    fun englishQuery(word: String): String {

        var str = word

        try {

            // string Space First
            if (QueryUtils.isUncode(str)) {
                str = QueryUtils.ubahUncode(str)
            }

            // string Space First
            if (QueryUtils.isSpaceFirst(str)) {
                str = QueryUtils.ubahSpaceFirst(str)
            }

            // string Space Last
            if (QueryUtils.isSpaceLast(str)) {
                str = QueryUtils.ubahSpaceLast(str)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return " english LIKE  '%" + str + "%' ORDER BY (" +
                "CASE WHEN english = '" + str + "' " +
                "THEN 1 WHEN english LIKE '" + str + ",%' " +
                "   OR english LIKE '" + str + " ,%' " +
                "THEN 2 WHEN  english LIKE '%," + str + "' " +
                "THEN 3 WHEN english LIKE '%," + str + ",%' " +
                "OR english LIKE '%," + str + " ,%' " +
                "OR english LIKE '%, " + str + ",%' " +
                "OR english LIKE '%, " + str + " ,%' " +
                "THEN 4 WHEN english LIKE '" + str + " %' " +
                "THEN 5 WHEN english LIKE '" + str + "%'  " +
                "THEN 6 ELSE 7 END)"

    }


    fun checkSaraf(paramString: String): String? {
        try {

            if (paramString.length == 4) {

                // افعل
                if (paramString[0] == 'ا') {
                    return strUbah(paramString, intArrayOf(0))
                }

                // يفعل
                if (paramString[0] == 'ي') {
                    return strUbah(paramString, intArrayOf(0))
                }

                // مفعل
                if (paramString[0] == 'م') {
                    return strUbah(paramString, intArrayOf(0))
                }

                // فعول
                if (paramString[2] == 'و') {
                    return strUbah(paramString, intArrayOf(2))
                }

                // فعيل
                if (paramString[2] == 'ي') {
                    return strUbah(paramString, intArrayOf(2))
                }

                // فعال
                if (paramString[2] == 'ا') {
                    return strUbah(paramString, intArrayOf(2))
                }


            } else if (paramString.length == 5) {


                // فاعلة
                if (paramString[1] == 'ا' && paramString[4] == 'ة') {
                    return strUbah(paramString, intArrayOf(1, 4))
                }

                // فعلان
                if (paramString[3] == 'ا' && paramString[4] == 'ن') {
                    return strUbah(paramString, intArrayOf(3, 4))
                }

                // فعالة
                if (paramString[2] == 'ا' && paramString[4] == 'ة') {
                    return strUbah(paramString, intArrayOf(2, 4))
                }


                // تفاعل
                if (paramString[0] == 'ت' && paramString[2] == 'ا') {
                    return strUbah(paramString, intArrayOf(0, 2))
                }

                // يفاعل
                if (paramString[0] == 'ي' && paramString[2] == 'ا') {
                    return strUbah(paramString, intArrayOf(0, 2))
                }

                // مفاعل
                if (paramString[0] == 'م' && paramString[2] == 'ا') {
                    return strUbah(paramString, intArrayOf(0, 2))
                }

                // مفعول
                if (paramString[0] == 'م' && paramString[3] == 'و') {
                    return strUbah(paramString, intArrayOf(0, 3))
                }

                // مفعال
                if (paramString[0] == 'م' && paramString[3] == 'ا') {
                    return strUbah(paramString, intArrayOf(0, 3))
                }

            } else if (paramString.length == 6) {

                // افتعال
                if (paramString[0] == 'ا' && paramString[2] == 'ت' && paramString[4] == 'ا') {
                    return strUbah(paramString, intArrayOf(0, 2, 4))
                }

                // افاعيل
                if (paramString[0] == 'ا' && paramString[2] == 'ا' && paramString[4] == 'ي') {
                    return strUbah(paramString, intArrayOf(0, 2, 4))
                }

                // استفعل
                if (paramString[0] == 'ا' && paramString[1] == 'س' && paramString[2] == 'ت') {
                    return strUbah(paramString, intArrayOf(0, 1, 2))
                }

                // يستفعل
                if (paramString[0] == 'ي' && paramString[1] == 'س' && paramString[2] == 'ت') {
                    return strUbah(paramString, intArrayOf(0, 1, 2))
                }

                // مفاعيل
                if (paramString[0] == 'م' && paramString[2] == 'ا' && paramString[4] == 'ي') {
                    return strUbah(paramString, intArrayOf(0, 2, 4))
                }

                // مفاعلة
                if (paramString[0] == 'م' && paramString[2] == 'ا' && paramString[5] == 'ة') {
                    return strUbah(paramString, intArrayOf(0, 2, 5))
                }

                // مستفعل
                if (paramString[0] == 'م' && paramString[1] == 'س' && paramString[2] == 'ت') {
                    return strUbah(paramString, intArrayOf(0, 1, 2))
                }

                // مفعلون
                if (paramString[0] == 'م' && paramString[4] == 'و' && paramString[5] == 'ن') {
                    return strUbah(paramString, intArrayOf(0, 4, 5))
                }

                // متفاعل
                if (paramString[0] == 'م' && paramString[1] == 'ت' && paramString[3] == 'ا') {
                    return strUbah(paramString, intArrayOf(0, 1, 3))
                }

                // يتفاعل
                if (paramString[0] == 'ي' && paramString[1] == 'ت' && paramString[3] == 'ا') {
                    return strUbah(paramString, intArrayOf(0, 1, 3))
                }

            } else if (paramString.length == 7) {


                // متفاعلة
                if (paramString[0] == 'م' && paramString[1] == 'ت' && paramString[3] == 'ا' && paramString[6] == 'ة') {
                    return strUbah(paramString, intArrayOf(0, 1, 3, 6))
                }

                // استفعال
                if (paramString[0] == 'ا' && paramString[1] == 'س' && paramString[2] == 'ت' && paramString[5] == 'ا') {
                    return strUbah(paramString, intArrayOf(0, 1, 2, 5))
                }


            } else if (paramString.length == 8) {

                // استفعلان
                if (paramString[0] == 'ا' && paramString[1] == 'س' && paramString[2] == 'ت' && paramString[6] == 'ا' && paramString[7] == 'ن') {
                    return strUbah(paramString, intArrayOf(0, 1, 2, 6, 7))
                }

                // استفعلين
                if (paramString[0] == 'ا' && paramString[1] == 'س' && paramString[2] == 'ت' && paramString[6] == 'ي' && paramString[7] == 'ن') {
                    return strUbah(paramString, intArrayOf(0, 1, 2, 6, 7))
                }

                // استفعلون
                if (paramString[0] == 'ا' && paramString[1] == 'س' && paramString[2] == 'ت' && paramString[6] == 'و' && paramString[7] == 'ن') {
                    return strUbah(paramString, intArrayOf(0, 1, 2, 6, 7))
                }

                // يستفعلون
                if (paramString[0] == 'ي' && paramString[1] == 'س' && paramString[2] == 'ت' && paramString[6] == 'و' && paramString[7] == 'ن') {
                    return strUbah(paramString, intArrayOf(0, 1, 2, 6, 7))
                }

                // يستفعلين
                if (paramString[0] == 'ي' && paramString[1] == 'س' && paramString[2] == 'ت' && paramString[6] == 'ي' && paramString[7] == 'ن') {
                    return strUbah(paramString, intArrayOf(0, 1, 2, 6, 7))

                }

                // يستفعلان
                if (paramString[0] == 'ي' && paramString[1] == 'س' && paramString[2] == 'ت' && paramString[6] == 'ا' && paramString[7] == 'ن') {
                    return strUbah(paramString, intArrayOf(0, 1, 2, 6, 7))
                }

                // مستفعلون
                if (paramString[0] == 'م' && paramString[1] == 'س' && paramString[2] == 'ت' && paramString[6] == 'و' && paramString[7] == 'ن') {
                    return strUbah(paramString, intArrayOf(0, 1, 2, 6, 7))
                }

                // مستفعلان
                if (paramString[0] == 'م' && paramString[1] == 'س' && paramString[2] == 'ت' && paramString[6] == 'ا' && paramString[7] == 'ن') {
                    return strUbah(paramString, intArrayOf(0, 1, 2, 6, 7))
                }

                // مستفعلين
                if (paramString[0] == 'م' && paramString[1] == 'س' && paramString[2] == 'ت' && paramString[6] == 'ي' && paramString[7] == 'ن') {
                    return strUbah(paramString, intArrayOf(0, 1, 2, 6, 7))
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    // check huruf (ا)
    fun isAlifSahaja(paramString: String): Boolean? {
        var localBoolean: Boolean? = false

        if (paramString.length >= 1) {
            if (paramString[0] == 'ا' || paramString[0] == 'أ') {
                localBoolean = true
            }
        }
        return localBoolean
    }

    // check huruf (ا) bukan (ل)
    internal fun isAlif(paramString: String): Boolean? {
        var localBoolean: Boolean? = false

        if (paramString.length > 2) {
            if (paramString[0] == 'ا' && paramString[1] != 'ل') {
                localBoolean = true
            }
        }
        return localBoolean
    }

    // check huruf (ال)
    fun isAlifLam(paramString: String): Boolean? {
        var localBoolean: Boolean? = false

        if (paramString.length > 2) {
            if (paramString[0] == 'ا' && paramString[1] == 'ل') {
                localBoolean = true
            }
        }
        return localBoolean
    }

    // check mudhari
    internal fun isMudhari(paramString: String): Boolean {
        if (paramString.length > 3) {
            val ya = paramString[0]

            return ya == 'ي' || ya == 'ت'

        }
        return false
    }

    // check code
    internal fun isUncode(paramString: String): Boolean {

        for (i in 0 until paramString.length) {

            val code = paramString[i]

            if (code == '!' ||
                    code == '@' ||
                    code == '#' ||
                    code == '$' ||
                    code == '%' ||
                    code == '*' ||
                    code == '^' ||
                    code == '&' ||
                    code == '(' ||
                    code == ')' ||
                    code == '+' ||
                    code == '=' ||
                    code == '-' ||
                    code == '±' ||
                    code == '/' ||
                    code == '>' ||
                    code == '<' ||
                    code == '_' ||
                    code == '"' ||
                    code == '\'' ||
                    code == '\\') {
                return true
            }
        }

        return false
    }

    // check space first
    private fun isSpaceFirst(paramString: String): Boolean {

        if (paramString.length > 1) {

            val space = paramString[0]

            return space == ' '

        }
        return false
    }

    // check space last
    private fun isSpaceLast(paramString: String): Boolean {

        if (paramString.length > 1) {

            val space = paramString[paramString.length - 1]

            return space == ' '

        }
        return false
    }

    // check mad last
    private fun isMadLast(paramString: String): Boolean? {
        var localBoolean: Boolean? = false

        if (paramString.length > 1) {
            val ta = paramString[paramString.length - 1]

            if (ta == 'ة' || ta == 'ت' || ta == 'ا' || ta == 'و' || ta == 'ن' || ta == 'ي' || ta == 'ى') {
                localBoolean = true
            }

        }
        return localBoolean
    }

    // check mad last
    private fun isJamaLast(paramString: String): Boolean {

        if (paramString.length > 3 && paramString.length < 11) {

            val x1 = paramString[paramString.length - 2]
            val x2 = paramString[paramString.length - 1]

            return x1 == 'ا' && x2 == 'ت' || x1 == 'ا' && x2 == 'ن' || x1 == 'ي' && x2 == 'ن' || x1 == 'و' && x2 == 'ن' || x1 == 'و' && x2 == 'ا'

        }
        return false
    }

    // check tanis last
    private fun isLastTanis(paramString: String): Boolean? {
        var localBoolean: Boolean? = false

        if (paramString.length > 1) {
            val ta = paramString[paramString.length - 1]

            if (ta == 'ة' || ta == 'ه') {
                localBoolean = true
            }

        }
        return localBoolean
    }

    // check ya mad last
    private fun isLastYaMad(paramString: String): Boolean? {
        var localBoolean: Boolean? = false

        if (paramString.length > 1) {
            val ta = paramString[paramString.length - 1]

            if (ta == 'ي' || ta == 'ى') {
                localBoolean = true
            }

        }
        return localBoolean
    }

    // ubah huruf (ا) -> (أ)
    private fun ubahHamzahFa(paramString: String): String {

        val str = StringBuilder()

        for (i in 0 until paramString.length) {
            var strChar = paramString[i]

            if (paramString[i] == 'ا' && i == 0) {

                strChar = 'أ'

            }

            str.append(strChar)
        }
        return str.toString()
    }

    // ubah huruf (ا) -> (إ)
    private fun ubahHamzahKa(paramString: String): String {

        val str = StringBuilder()

        for (i in 0 until paramString.length) {
            var strChar = paramString[i]

            if (paramString[i] == 'ا' && i == 0) {

                strChar = 'إ'

            }
            str.append(strChar)
        }
        return str.toString()
    }

    // ubah huruf (ا) -> (آ)
    private fun ubahHamzahMa(paramString: String): String {

        val str = StringBuilder()

        for (i in 0 until paramString.length) {
            var strChar = paramString[i]

            if (paramString[i] == 'ا' && i == 0) {

                strChar = 'آ'

            }
            str.append(strChar)
        }
        return str.toString()
    }

    // ubah huruf (ا) -> (إ)
    fun Provider(paramString: String): String {

        val str = StringBuilder()

        for (i in 0 until paramString.length) {
            var strChar = paramString[i]

            if (paramString[i] == 'Z') {

                strChar = ' '

            }
            str.append(strChar)
        }
        return str.toString()
    }

    // ubah huruf (ي) <-> (ت)
    internal fun ubahMudhari(paramString: String): String {

        val str = StringBuilder()

        for (i in 0 until paramString.length) {

            var strChar = paramString[i]

            if (strChar == 'ي' && i == 0) {

                strChar = 'ت'

            } else if (strChar == 'ت' && i == 0) {

                strChar = 'ي'

            }

            str.append(strChar)
        }

        return str.toString()
    }

    private fun ubahAlifLam(paramString: String): String {
        val str = StringBuilder()

        for (i in 2 until paramString.length) {
            str.append(paramString[i])
        }

        return str.toString()
    }

    private fun ubahLast(paramString: String): String {

        val paramStr = StringBuilder()

        for (i in 0 until paramString.length - 1) {
            paramStr.append(paramString[i])
        }

        return paramStr.toString()
    }

    // ubah code
    private fun ubahUncode(paramString: String): String {

        val strChar = StringBuilder()

        for (i in 0 until paramString.length) {

            val code = paramString[i]

            if (code == '!' ||
                    code == '@' ||
                    code == '#' ||
                    code == '$' ||
                    code == '%' ||
                    code == '*' ||
                    code == '^' ||
                    code == '&' ||
                    code == '(' ||
                    code == ')' ||
                    code == '+' ||
                    code == '=' ||
                    code == '±' ||
                    code == '/' ||
                    code == '>' ||
                    code == '<' ||
                    code == '_' ||
                    code == '"' ||
                    code == '\'' ||
                    code == '\\') {

                strChar.append("")

            } else {

                strChar.append(code)

            }
        }

        return strChar.toString()
    }

    private fun ubahSpaceFirst(paramString: String): String {
        var c1: Char

        var x1 = false
        val x2 = paramString.length

        var paramStr = StringBuilder()

        for (i in 0 until x2) {
            c1 = paramString[i]

            if (c1 != ' ' || x1) {

                paramStr.append(c1)
                x1 = true

            }

        }

        if (isSpaceFirst(paramStr.toString())) {
            paramStr = StringBuilder(ubahSpaceFirst(paramStr.toString()))
        }

        return paramStr.toString()
    }

    private fun ubahTanisLast(paramString: String): String {

        val paramStr = StringBuilder()

        var c1: Char

        for (i in 0 until paramString.length) {

            c1 = paramString[i]


            if (i == paramString.length - 1) {

                if (c1 == 'ة') {

                    c1 = 'ه'

                } else {

                    c1 = 'ة'

                }
            }

            paramStr.append(c1)

        }

        return paramStr.toString()
    }

    private fun ubahYaMadLast(paramString: String): String {

        val paramStr = StringBuilder()

        var c1: Char

        for (i in 0 until paramString.length) {

            c1 = paramString[i]


            if (i == paramString.length - 1) {

                if (c1 == 'ي') {

                    c1 = 'ى'

                } else {

                    c1 = 'ي'

                }
            }

            paramStr.append(c1)

        }

        return paramStr.toString()
    }

    // check jama last
    private fun ubahJamaLast(paramString: String): String {
        val strChar = StringBuilder()

        for (i in 0 until paramString.length - 2) {

            strChar.append(paramString[i])

        }
        return strChar.toString()
    }

    // ubah space dari awal
    private fun ubahSpaceLast(paramString: String): String {
        var c1: Char

        var x1 = false
        val x2 = paramString.length - 1

        var paramStr = StringBuilder()

        for (i in x2 downTo -1 + 1) {
            c1 = paramString[i]

            if (c1 != ' ' || x1) {

                paramStr.append(c1)
                x1 = true

            }

        }

        if (isSpaceFirst(paramStr.toString())) {
            paramStr = StringBuilder(ubahSpaceFirst(paramStr.toString()))
        }

        return ubahTextRevice(paramStr.toString())
    }

    // ubah perkataan mengikut dari belakang
    private fun ubahTextRevice(paramString: String): String {

        val paramStr = StringBuilder()

        for (i in paramString.length - 1 downTo -1 + 1) {
            paramStr.append(paramString[i])

        }

        return paramStr.toString()

    }

    // ubah & hilangkan perkatan mengikut position
    private fun strUbah(str: String, postion: IntArray): String {

        val paramStr = StringBuilder()

        var count = 0

        for (i in 0 until str.length) {
            val c = str[i]

            if (postion[count] == i) {
                if (count < postion.size - 1) {
                    count++
                }
                paramStr.append("")

            } else {
                paramStr.append(c)
            }
        }

        return paramStr.toString()
    }

    fun empty(paramString: String): String {

        var c1: Char

        val paramStr = StringBuilder("")

        for (i in 0 until paramString.length) {
            c1 = paramString[i]

            if (c1 != ' ') {

                paramStr.append(c1)

            }

        }

        return paramStr.toString()
    }


}
