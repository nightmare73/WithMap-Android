package com.ebookfrenzy.withmap.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.view.hamburg.NotificationFragment

class PracActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prac)

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.fl_prac_act, NotificationFragment())
        ft.commit()
    }
}
