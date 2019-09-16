package com.ebookfrenzy.withmap.view.intro

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import androidx.viewpager.widget.ViewPager
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentTutorialBinding
import com.ebookfrenzy.withmap.view.intro.adapter.TutorialStatePagerAdapter

/**
 * Created By Yun Hyeok
 * on 9월 15, 2019
 */

class TutorialActivity : AppCompatActivity(), TutorialActivityClickListener,
    ViewPager.OnPageChangeListener {

    override var currentPosition = ObservableInt().apply { set(0) }

    lateinit var binding: FragmentTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_tutorial)
        binding.apply {
            listener = this@TutorialActivity
            position = currentPosition
        }
        initView()
    }

    override fun onExitClicked(v: View) {
        finish()
    }

    override fun onNextClicked(v: View) {
        binding.vpTutorialFragContents.currentItem = 1
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        currentPosition.set(position)
    }

    private fun initView() {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        binding.vpTutorialFragContents.apply {
            adapter = TutorialStatePagerAdapter(supportFragmentManager)
            addOnPageChangeListener(this@TutorialActivity)
        }
    }
}

interface TutorialActivityClickListener {

    var currentPosition: ObservableInt

    fun onExitClicked(v: View)

    fun onNextClicked(v: View)

}