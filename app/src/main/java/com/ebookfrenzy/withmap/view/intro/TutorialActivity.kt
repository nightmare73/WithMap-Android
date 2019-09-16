package com.ebookfrenzy.withmap.view.intro

import android.app.ActionBar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentTutorialBinding
import com.ebookfrenzy.withmap.view.intro.adapter.TutorialStatePagerAdapter

/**
 * Created By Yun Hyeok
 * on 9월 15, 2019
 */

class TutorialActivity : AppCompatActivity(), TutorialActivityClickListener {

    override var currentPosition: Int = 1

    lateinit var binding: FragmentTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_tutorial)
        binding.listener = this
        initView()

    }

    override fun onExitClicked(v: View) {
        finish()
    }

    override fun onNextClicked(v: View) {

    }

    private fun initView() {
        binding.vpTutorialFragContents
            .adapter = TutorialStatePagerAdapter(supportFragmentManager)
        window.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}

interface TutorialActivityClickListener {

    var currentPosition: Int

    fun onExitClicked(v: View)

    fun onNextClicked(v: View)
}