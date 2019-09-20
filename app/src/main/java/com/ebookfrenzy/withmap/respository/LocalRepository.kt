package com.ebookfrenzy.withmap.respository

/**
 * Created By Yun Hyeok
 * on 9월 18, 2019
 */

class LocalRepository(private val sp: SharedPreferenceSource) {

    fun getFirstFlag(): Boolean {
        val isFirst = sp.firstFlag
        if (isFirst) {
            sp.firstFlag = false
        }
        return isFirst
    }

}