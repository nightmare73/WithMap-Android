package com.ebookfrenzy.withmap.view.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ebookfrenzy.withmap.databinding.FragmentTutorialBinding

/**
 * Created By Yun Hyeok
 * on 9월 15, 2019
 */

class TutorialFragment : Fragment() {

    lateinit var binding: FragmentTutorialBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorialBinding.inflate(inflater)
        return binding.root
    }



}