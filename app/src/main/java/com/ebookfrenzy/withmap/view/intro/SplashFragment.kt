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

    private val handler = Handler()
    private lateinit var thread: Thread

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thread = Thread {
            Navigation.findNavController(view)
                .navigate(R.id.action_splashFragment_to_searchFragment2)
        }
        handler.postDelayed(thread, 1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(thread)
    }
    // 켜지는동안 백그라운드 들어가면 안넘어가는 버그를 해결하기 위해 넣엇으나 해결이 안됨 ;;
}
