package com.ebookfrenzy.withmap.view.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.databinding.FragmentSearchBinding
import com.ebookfrenzy.withmap.network.KakaoService
import com.ebookfrenzy.withmap.viewmodel.SearchViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created By Yun Hyeok
 * on 9월 13, 2019
 */

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by viewModel()

    private val onBackClicked = View.OnClickListener {

        val action = SearchFragmentDirections.actionSearchFragmentToPinDetailFragment("넘어가나?")
        Navigation.findNavController(it).navigate(action)
        //Navigation.findNavController(it).popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater).apply {
            vm = viewModel
            returnBack = onBackClicked
            lifecycleOwner = this@SearchFragment
        }
        tempSubcribe()
        return binding.root
    }


    override fun onResume() {
        Log.d("Malibin Debug","SearchFragment의 Livedata : ${viewModel.tempSharedData.value}")
        super.onResume()
    }
    fun tempSubcribe() {
        viewModel.tempSharedData.observe(viewLifecycleOwner, Observer {
            binding.etSearchFragQuery.setText(it)
        })
    }
}