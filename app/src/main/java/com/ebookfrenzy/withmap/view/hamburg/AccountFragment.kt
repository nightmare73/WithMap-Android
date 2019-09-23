package com.ebookfrenzy.withmap.view.hamburg


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentAccountBinding

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAccountBinding.inflate(LayoutInflater.from(this.context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run{
            fragment = this@AccountFragment
        }
    }

    fun popBackStack() {
        binding.root.findNavController().popBackStack()
    }


}
