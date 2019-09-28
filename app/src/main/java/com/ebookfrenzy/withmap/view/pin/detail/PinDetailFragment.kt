package com.ebookfrenzy.withmap.view.pin.detail

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.databinding.FragmentPinDetailBinding
import com.ebookfrenzy.withmap.network.response.PinDetail
import com.ebookfrenzy.withmap.view.dialog.PlainDialog
import com.ebookfrenzy.withmap.viewmodel.PinDetailViewModel
import kotlinx.android.synthetic.main.fragment_pin_detail.view.*
import org.koin.android.ext.android.inject

/**
 * Created By Yun Hyeok
 * on 9월 13, 2019
 */

class PinDetailFragment : Fragment() {

    private lateinit var originalWindowAttributes: WindowManager.LayoutParams

    private val viewModelFactory: ViewModelProvider.Factory by inject()
    private lateinit var viewModel: PinDetailViewModel

    private val returnBack = View.OnClickListener {
        Navigation.findNavController(it).popBackStack()
        resetStatusBarSettings()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[PinDetailViewModel::class.java]

        val binding = FragmentPinDetailBinding.inflate(inflater)
        binding.returnBack = returnBack
        binding.vm = viewModel
        binding.lifecycleOwner = this

        subscribePinDetail(binding)
        subscribeIsRecommended(binding)
        subscribeIsReported()

        viewModel.getPinDetail(4)

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

    private fun bindView(binding: FragmentPinDetailBinding, pinDetail: PinDetail) {
        val isMyPin = pinDetail.mine
        val pinId = pinDetail.pin.id
        if (isMyPin) {
            binding.btnPinDetailFragHelped.setOnClickListener { deployDialog("자신의 글은 추천할 수 없습니다.") }
            binding.btnPinDetailFragReport.setOnClickListener { deployDialog("자신의 글은 신고할 수 없습니다.") }
            return
        }
        binding.btnPinDetailFragHelped.setOnClickListener { viewModel.recommendPin(pinId) }
        binding.btnPinDetailFragReport.setOnClickListener { viewModel.reportPin(pinId) }
    }

    private fun subscribePinDetail(binding: FragmentPinDetailBinding) {
        viewModel.pinDetail.observe(viewLifecycleOwner, Observer {
            bindView(binding, it)

            val isRecommended = it.recommended
            binding.btnPinDetailFragHelped.isSelected = isRecommended
            if (isRecommended) {
                binding.btnPinDetailFragHelped.setOnClickListener(null)
            }
        })
    }

    private fun subscribeIsRecommended(binding: FragmentPinDetailBinding) {
        viewModel.isRecommended.observe(viewLifecycleOwner, Observer { isRecommended ->
            binding.btnPinDetailFragHelped.isSelected = isRecommended
            if (isRecommended) {
                binding.tvPinDetailFragLikeCount.let {
                    it.text = (it.text.toString().toInt() + 1).toString()
                }
                binding.btnPinDetailFragHelped.setOnClickListener(null)
                return@Observer
            }
            Toast.makeText(context!!, "서버통신에 실패하였습니다", Toast.LENGTH_SHORT).show()
        })
    }

    private fun subscribeIsReported() {
        viewModel.isReported.observe(viewLifecycleOwner, Observer { isReported ->
            if (isReported) {
                Toast.makeText(context!!, "신고가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                return@Observer
            }
            Toast.makeText(context!!, "이미 신고되었습니다.", Toast.LENGTH_SHORT).show()
        })
    }

    private fun deployDialog(content: String) {
        PlainDialog(context!!).apply {
            this.content = content
        }.show()
    }

}