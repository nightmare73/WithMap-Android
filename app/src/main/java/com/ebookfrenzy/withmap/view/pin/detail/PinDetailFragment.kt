package com.ebookfrenzy.withmap.view.pin.detail

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.databinding.FragmentPinDetailBinding
import com.ebookfrenzy.withmap.viewmodel.PinDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created By Yun Hyeok
 * on 9월 13, 2019
 */

class PinDetailFragment : Fragment() {

    private lateinit var originalWindowAttributes: WindowManager.LayoutParams

    private val viewModel: PinDetailViewModel by viewModel()

    private val returnBack = View.OnClickListener {
        Navigation.findNavController(it).popBackStack()
        resetStatusBarSettings()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentPinDetailBinding.inflate(inflater)
        binding.returnBack = returnBack
        binding.vm = viewModel

        viewModel.getPinDetail(1)

        subscribePinDetail(binding)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarTransparent()
    }

    override fun onDetach() {
        super.onDetach()
        resetStatusBarSettings()
    }

    private fun setStatusBarTransparent() {
        originalWindowAttributes = activity!!.window.attributes
        activity!!.window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            if (Build.VERSION.SDK_INT >= 21) {
                statusBarColor = Color.TRANSPARENT
            }
        }
    }

    private fun resetStatusBarSettings() {
        activity!!.window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            attributes = originalWindowAttributes
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = activity!!.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
            return
        }
        winParams.flags = winParams.flags and bits.inv()
        win.attributes = winParams
    }

    private fun subscribePinDetail(binding: FragmentPinDetailBinding) {

    }

}