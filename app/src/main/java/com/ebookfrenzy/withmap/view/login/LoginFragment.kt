package com.ebookfrenzy.withmap.view.login


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.R

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // -혁- 회원가입 화면으로 넘어가는 코드
//        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
//            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signUpFragment)
//        }

    }


}
