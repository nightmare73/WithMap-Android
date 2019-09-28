package com.ebookfrenzy.withmap.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ebookfrenzy.withmap.R

/**
 * Created By Yun Hyeok
 * on 9월 28, 2019
 */

class PlainDialog(context: Context) : Dialog(context) {
    var content: String = ""
    var buttonText: String = "확인"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_plain)

        window!!.setBackgroundDrawableResource(R.color.transparent)
        initView()
    }

    private fun initView() {
        val button = findViewById<ConstraintLayout>(R.id.btn_plain_dialog)
        button.setOnClickListener { dismiss() }

        val tvContent = findViewById<TextView>(R.id.tv_plain_dialog_content)
        tvContent.text = content

        val tvButton = findViewById<TextView>(R.id.tv_plain_dialog_button)
        tvButton.text = buttonText
    }
}