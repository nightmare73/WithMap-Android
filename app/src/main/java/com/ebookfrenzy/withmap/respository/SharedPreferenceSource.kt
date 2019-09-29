package com.ebookfrenzy.withmap.respository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.SharedPreferencesCompat
import com.google.android.gms.common.util.SharedPreferencesUtils

/**
 * Created By Yun Hyeok
 * on 9월 18, 2019
 */

class SharedPreferenceSource(context: Context) {

    private val FILE_NAME = "WithMap"
    private val sharedPreference = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreference.edit()
    private val myAuth = "myAuth"
    private val USER_ID = "user_id"
    private val userSharedPref = context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE)

    var userId : Int
    get() = userSharedPref.getInt("id", 0)
    set(id) = userSharedPref.edit().putInt("id", id).apply()

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
        editor.putString("authToken", token)
        editor.commit()
    }
}