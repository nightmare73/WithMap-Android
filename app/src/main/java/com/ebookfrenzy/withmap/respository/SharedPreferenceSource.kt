package com.ebookfrenzy.withmap.respository

import android.content.Context

/**
 * Created By Yun Hyeok
 * on 9월 18, 2019
 */

class SharedPreferenceSource(context: Context) {

    private val FILE_NAME = "WithMap"
    private val sharedPreference = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreference.edit()

    fun saveFirstAccessFlagFalse() {
        editor.putBoolean("isFirst", false)
        editor.commit()
    }

    fun getFirstAccessFlag() = sharedPreference.getBoolean("isFirst", true)
}