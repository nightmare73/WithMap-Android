package com.ebookfrenzy.withmap.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentSearchBinding
import com.ebookfrenzy.withmap.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created By Yun Hyeok
 * on 9월 13, 2019
 */

class SearchFragment : Fragment() {

    val viewModel : SearchViewModel by viewModel()

    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        binding.fragment = this
        return binding.root
    }

    fun onBackClicked(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_searchFragment_to_pinDetailFragment)
    }
}