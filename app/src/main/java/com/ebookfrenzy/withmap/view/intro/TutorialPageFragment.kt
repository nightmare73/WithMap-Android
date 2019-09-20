package com.ebookfrenzy.withmap.view.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * Created By Yun Hyeok
 * on 9월 15, 2019
 */

class TutorialPageFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(@LayoutRes layoutId: Int) = TutorialPageFragment().apply {
            arguments = Bundle().apply {
                putInt("layoutId", layoutId)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = arguments!!.getInt("layoutId")
        return LayoutInflater.from(context).inflate(layoutId, container, false)
    }
}