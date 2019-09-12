package com.ebookfrenzy.withmap.view.hamburg


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.ebookfrenzy.withmap.BR
import com.ebookfrenzy.withmap.R

import com.ebookfrenzy.withmap.data.NotificationData
import com.ebookfrenzy.withmap.databinding.FragmentNotificationBinding
import com.ebookfrenzy.withmap.databinding.ItemNotificationBinding
import com.ebookfrenzy.withmap.viewmodel.NotificationViewModel
import com.googry.googrybaserecyclerview.BaseRecyclerView

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            lifecycleOwner = this@NotificationFragment
            rvNotification.adapter =
                object : BaseRecyclerView.Adapter<NotificationData, ItemNotificationBinding>(
                    R.layout.item_notification,
                    BR.notification
                ) {}
            vm = ViewModelProviders.of(this@NotificationFragment)[NotificationViewModel::class.java]
        }
    }
}
