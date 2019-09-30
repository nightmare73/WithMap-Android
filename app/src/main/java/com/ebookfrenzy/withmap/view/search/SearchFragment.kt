package com.ebookfrenzy.withmap.view.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.databinding.FragmentSearchBinding
import com.ebookfrenzy.withmap.viewmodel.SearchViewModel
import org.koin.android.ext.android.inject

/**
 * Created By Yun Hyeok
 * on 9월 13, 2019
 */

class SearchFragment : Fragment(), TextView.OnEditorActionListener {

    private val viewModelFactory: ViewModelProvider.Factory by inject()
    private lateinit var viewModel: SearchViewModel

    private val onBackClicked = View.OnClickListener {
        Navigation.findNavController(it).popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java]

        val binding = FragmentSearchBinding.inflate(inflater).apply {
            vm = viewModel
            returnBack = onBackClicked
            lifecycleOwner = this@SearchFragment
        }

        val rvAdapter = SearchAdapter()
        binding.rvSearchFragResult.adapter = rvAdapter.apply { setViewModel(viewModel) }
        binding.etSearchFragQuery.setOnEditorActionListener(this)

        searchResultSubscribe(rvAdapter)

        return binding.root
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        when (actionId) {
            EditorInfo.IME_ACTION_SEARCH -> {
                viewModel.getSearchResult(v?.text.toString())
            }
        }
        return true
    }

    private fun searchResultSubscribe(adapter: SearchAdapter) {
        viewModel.searchResult.observe(viewLifecycleOwner, Observer {
            val searchResults = it.map { kakaoResult -> kakaoResult.toSearchLocationResult() }
            adapter.submitList(searchResults)
        })
    }
}