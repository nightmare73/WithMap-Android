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

    var firstFlag: Boolean
        get() = sharedPreference.getBoolean("isFirst", true)
        set(flag) = saveFirstAccessFlag(flag)

    var authToken: String
        get() = sharedPreference.getString("authToken", "")!!
        set(token) = saveAuthToken(token)


    private fun saveFirstAccessFlag(flag: Boolean) {
        editor.putBoolean("isFirst", flag)
        editor.commit()
    }

    private fun saveAuthToken(token: String) {
        editor.putString("authToken", "")
        editor.commit()
    }
}