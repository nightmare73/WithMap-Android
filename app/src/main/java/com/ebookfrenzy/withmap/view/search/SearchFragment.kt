package com.ebookfrenzy.withmap.view.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.config.diModules
import com.ebookfrenzy.withmap.databinding.FragmentSearchBinding
import com.ebookfrenzy.withmap.network.KakaoService
import com.ebookfrenzy.withmap.network.response.KakaoKeywordAddressResponse
import com.ebookfrenzy.withmap.viewmodel.SearchViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created By Yun Hyeok
 * on 9월 13, 2019
 */

class SearchFragment : Fragment() {

    val viewModel: SearchViewModel by viewModel()

    val tempKakaoService: KakaoService by inject()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tempKakaoService.getKakaoKeywordAdressRequest("서울역 위워크").enqueue(object :
            Callback<KakaoKeywordAddressResponse> {
            override fun onFailure(call: Call<KakaoKeywordAddressResponse>, t: Throwable) {
                Log.d("Malibin Debug","실패")
            }

            override fun onResponse(
                call: Call<KakaoKeywordAddressResponse>,
                response: Response<KakaoKeywordAddressResponse>
            ) {
                Log.d("Malibin Debug","성공")
                Log.d("Malibin Debug","${response.body()}")
                Log.d("Malibin Debug","${response.raw()}")
            }
        })
    }

    fun onBackClicked(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_searchFragment_to_pinDetailFragment)
    }
}