package com.ebookfrenzy.withmap.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ebookfrenzy.withmap.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.add(R.id.fragment_login_activity, SignUpFragment())
        fragmentTransaction.commit()
    }
}
