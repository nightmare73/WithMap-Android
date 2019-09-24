package com.ebookfrenzy.withmap.view.login


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentSignUpInfoBinding
import kotlinx.android.synthetic.main.fragment_sign_up_info.*

/**
 * A simple [Fragment] subclass.
 */
class SignUpInfoFragment : Fragment() {
    private val TAG = "SignUpInfoFragment"


    var man: Int = 0
    var handicap: Int = 0
    private lateinit var binding: FragmentSignUpInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpInfoBinding.inflate(LayoutInflater.from(context))
        binding.lifecycleOwner = this
        binding.fragment = this

        np_sign_up_info_frag.run {
            minValue = 1800
            maxValue = 2019
            displayedValues
            wrapSelectorWheel = false
        }

        imageView1.setOnClickListener { onClickInfo(1) }
        imageView2.setOnClickListener { onClickInfo(2) }
        imageView3.setOnClickListener { onClickInfo(4) }
        imageView4.setOnClickListener { onClickInfo(3) }


        // -혁- 회원가입 다 끝난후 로그인 프래그먼트로 돌아가는 코드
//        view.findViewById<Button>(R.id.button).setOnClickListener {
//            Navigation.findNavController(it).navigate(R.id.action_signUpInfoFragment_to_loginFragment)
//        }
    }

    fun onClickInfo(i: Int) {

        when (i) {
            1 -> if (man == 0 || man == 2) {
                imageView1.setImageResource(R.drawable.join_male_touch)
                imageView2.setImageResource(R.drawable.join_female)
                man = 1
                Log.d(TAG, "onClickInfo(1)")
            } else {
                imageView1.setImageResource(R.drawable.join_male)
                man = 0
            }

            2 -> if (man == 1 || man == 0) {
                imageView2.setImageResource(R.drawable.join_female_touch)
                imageView1.setImageResource(R.drawable.join_male)
                man = 2
            } else {
                imageView2.setImageResource(R.drawable.join_female)
                man = 0
            }

            3 -> if (handicap == 0 || handicap == 1) {
                imageView3.setImageResource(R.drawable.join_walk_touch)
                imageView4.setImageResource(R.drawable.join_wheelchair)
                handicap = 2
                Log.d(TAG, "onClickInfo(3)")
            } else {
                imageView3.setImageResource(R.drawable.join_walk)
                handicap = 0
            }
            4 -> if (handicap == 0 || handicap == 2) {
                imageView4.setImageResource(R.drawable.join_wheelchair_touch)
                imageView3.setImageResource(R.drawable.join_walk)
                handicap = 1
                Log.d(TAG, "onClickInfo(4)")
            } else {
                imageView4.setImageResource(R.drawable.join_wheelchair)
                handicap = 0
            }

        }
    }


//    fun onClickInfo(i: Int) {
//        var male: Boolean = false
//        var female: Boolean = false
//        var handicap: Boolean = false
//        var nonHandicap: Boolean = false
//
//        try {
//            when (i) {
//                1 -> {
//                    binding.imageView1.setImageResource(R.drawable.join_male_touch)
//                    Log.d(TAG, "onClickInfo(1)")
//                }
//            }
//        }catch (e : NoSuchElementException) {
//            Log.e("ERROR",e.message)
//        }
//
//    }


}
