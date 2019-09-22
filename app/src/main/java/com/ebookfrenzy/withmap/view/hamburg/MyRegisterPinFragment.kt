package com.ebookfrenzy.withmap.view.hamburg


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.BR

import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.data.MyRegisterPinData
import com.ebookfrenzy.withmap.databinding.FragmentMyRegisterPinBinding
import com.ebookfrenzy.withmap.viewmodel.NotificationViewModel
import com.googry.googrybaserecyclerview.BaseRecyclerView

/**
 * A simple [Fragment] subclass.
 */
class MyRegisterPinFragment : Fragment() {

    private val TAG = "MyRegisterPinFragment"
    private lateinit var binding : FragmentMyRegisterPinBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyRegisterPinBinding.inflate(LayoutInflater.from(this.context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run{
            vm = ViewModelProviders.of(this@MyRegisterPinFragment)[NotificationViewModel::class.java]
            lifecycleOwner = this@MyRegisterPinFragment
            rvMyRegister.adapter = object : BaseRecyclerView.Adapter<MyRegisterPinData, FragmentMyRegisterPinBinding>(
                R.layout.item_my_register,
                BR.myRegister
            ){}
        }
    }


}
