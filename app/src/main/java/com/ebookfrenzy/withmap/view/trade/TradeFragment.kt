package com.ebookfrenzy.withmap.view.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.R
import kotlinx.android.synthetic.main.fragment_trade.*

/**
 * Created By Yun Hyeok
 * on 9월 22, 2019
 */

class TradeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_trade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_trade_frag_back.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }
}