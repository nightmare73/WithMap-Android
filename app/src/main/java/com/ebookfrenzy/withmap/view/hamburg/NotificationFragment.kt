package com.ebookfrenzy.withmap.view.hamburg


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentNotificationBinding

/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {

    private val TAG = "NotificationFragment"

    private lateinit var binding : FragmentNotificationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentNotificationBinding.inflate(LayoutInflater.from(this.context))
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }


}
