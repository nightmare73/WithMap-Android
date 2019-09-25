package com.ebookfrenzy.withmap.view.search

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.databinding.FragmentSearchBinding
import com.ebookfrenzy.withmap.network.WithMapService
import com.ebookfrenzy.withmap.network.request.SignInParams
import com.ebookfrenzy.withmap.network.response.SignInResponse
import com.ebookfrenzy.withmap.viewmodel.SearchViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created By Yun Hyeok
 * on 9월 13, 2019
 */

class SearchFragment : Fragment(), TextView.OnEditorActionListener {

    private val dayeService: WithMapService by inject()

    private val viewModel: SearchViewModel by sharedViewModel()

    private val onBackClicked = View.OnClickListener {
        Navigation.findNavController(it).popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater).apply {
            vm = viewModel
            returnBack = onBackClicked
            lifecycleOwner = this@SearchFragment
        }

        val rvAdapter = SearchAdapter()
        binding.rvSearchFragResult.adapter = rvAdapter.apply {
            setViewModel(viewModel)
        }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dayeService.requestSignIn(SignInParams("test@naver.com", "12345678")).enqueue(object :
            Callback<SignInResponse> {
            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Log.d("Malibin Debug", "호롤리 ㅠㅠ : ${TextUtils.join("\n", t.stackTrace)}")
            }

            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                Log.d("Malibin Debug", "호롤리 : ${response.raw()}")
                Log.d("Malibin Debug", "호롤리 : ${response?.body()}")
            }
        })
    }

}