package com.ebookfrenzy.withmap.view.pin.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ebookfrenzy.withmap.databinding.FragmentPinDetailBinding

/**
 * Created By Yun Hyeok
 * on 9월 13, 2019
 */

class PinDetailFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentPinDetailBinding.inflate(inflater).root
    }
}