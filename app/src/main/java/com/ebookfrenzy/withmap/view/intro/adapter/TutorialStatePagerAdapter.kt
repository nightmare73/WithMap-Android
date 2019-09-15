package com.ebookfrenzy.withmap.view.intro.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.view.intro.TutorialPageFragment

/**
 * Created By Yun Hyeok
 * on 9월 15, 2019
 */

class TutorialStatePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val tutorialPages = listOf(
        TutorialPageFragment.newInstance(R.layout.fragment_tutorial_1),
        TutorialPageFragment.newInstance(R.layout.fragment_tutorial_2)
    )

    override fun getItem(position: Int): Fragment = tutorialPages[position]

    override fun getCount(): Int = tutorialPages.size
}