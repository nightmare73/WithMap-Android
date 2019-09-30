package com.ebookfrenzy.withmap.view.login


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentSignUpInfoBinding
import com.ebookfrenzy.withmap.network.request.SignUpParams
import com.ebookfrenzy.withmap.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_sign_up_info.*
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class SignUpInfoFragment : Fragment() {
    private val TAG = "SignUpInfoFragment"

    private var man: String = ""
    private var handicap: String = ""
    private var pickedYear: Int = 0

    private lateinit var signUpParams: SignUpParams

    private val viewModelFactory: ViewModelProvider.Factory by inject()
    private lateinit var viewModel: SignUpViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        SignUpInfoFragmentArgs.fromBundle(arguments!!).params?.let {
            signUpParams = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[SignUpViewModel::class.java]

        val binding = FragmentSignUpInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this

        subscribeIsSignUpSuccess(binding)

        initView(binding)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        np_sign_up_info_frag.run {
            minValue = 1900
            maxValue = 2019
            displayedValues
            wrapSelectorWheel = false
        }

        imageView1.setOnClickListener { onClickInfo(1) }
        imageView2.setOnClickListener { onClickInfo(2) }
        imageView3.setOnClickListener { onClickInfo(3) }
        imageView4.setOnClickListener { onClickInfo(4) }
    }

    private fun onClickInfo(i: Int) {

        when (i) {
            1 -> if (man == "" || man == "f") {
                imageView1.setImageResource(R.drawable.join_male_touch)
                imageView2.setImageResource(R.drawable.join_female)
                man = "m"
                Log.d(TAG, "onClickInfo(1)")
            } else {
                imageView1.setImageResource(R.drawable.join_male)
                man = ""
            }

            2 -> if (man == "m" || man == "") {
                imageView2.setImageResource(R.drawable.join_female_touch)
                imageView1.setImageResource(R.drawable.join_male)
                man = "f"
            } else {
                imageView2.setImageResource(R.drawable.join_female)
                man = ""
            }

            3 -> if (handicap == "" || handicap == "y") {
                imageView3.setImageResource(R.drawable.join_walk_touch)
                imageView4.setImageResource(R.drawable.join_wheelchair)
                handicap = "n"
                Log.d(TAG, "onClickInfo(3)")
            } else {
                imageView3.setImageResource(R.drawable.join_walk)
                handicap = ""
            }
            4 -> if (handicap == "" || handicap == "n") {
                imageView4.setImageResource(R.drawable.join_wheelchair_touch)
                imageView3.setImageResource(R.drawable.join_walk)
                handicap = "y"
                Log.d(TAG, "onClickInfo(4)")
            } else {
                imageView4.setImageResource(R.drawable.join_wheelchair)
                handicap = ""
            }
        }
        Log.d("Malibin Debug", "man : $man, handicap : $handicap")
    }

    private fun initView(binding: FragmentSignUpInfoBinding) {
        binding.button.setOnClickListener {
            val params = getSignUpParams()
            viewModel.requestSignUp(params)
            it.isEnabled = false
        }

        binding.npSignUpInfoFrag.setOnValueChangedListener { _, _, newVal ->
            pickedYear = newVal
        }
    }

    private fun subscribeIsSignUpSuccess(binding: FragmentSignUpInfoBinding) {
        binding.button.isEnabled = true
        viewModel.isSignUpSuccess.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
                backToLogin(binding.root)
                return@Observer
            }
            Toast.makeText(context, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
        })
    }

    private fun getSignUpParams(): SignUpParams {
        signUpParams.apply {
            disable = handicap
            gender = man
            year = pickedYear
        }
        return signUpParams
    }

    private fun backToLogin(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_signUpInfoFragment_to_loginFragment)
    }

}
