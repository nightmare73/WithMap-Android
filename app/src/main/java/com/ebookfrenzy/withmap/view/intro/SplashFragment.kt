package com.ebookfrenzy.withmap.view.intro

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.R

/**
 * Created By Yun Hyeok
 * on 9월 15, 2019
 */

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed(Thread {
            Navigation.findNavController(view)
                .navigate(R.id.action_splashFragment_to_searchFragment2)
        }, 1000)

    }
}
