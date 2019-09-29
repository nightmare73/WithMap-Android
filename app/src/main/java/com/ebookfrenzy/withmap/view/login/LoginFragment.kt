package com.ebookfrenzy.withmap.view.login


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentLoginBinding
import com.ebookfrenzy.withmap.network.request.SignInParams
import com.ebookfrenzy.withmap.respository.LocalRepository
import com.ebookfrenzy.withmap.viewmodel.LoginViewModel
import com.ebookfrenzy.withmap.viewmodel.PinDetailViewModel
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private val viewModelFactory: ViewModelProvider.Factory by inject()
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]

        val binding = FragmentLoginBinding.inflate(inflater)
        binding.vm = viewModel

        subscribeIsLoginSuccess()

        initView(binding)

        return binding.root
    }

    private fun initView(binding: FragmentLoginBinding) {
        binding.tvSignUp.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email = viewModel.inputEmail.get()
            val password = viewModel.inputPassword.get()
            if (email.isNullOrBlank() || password.isNullOrBlank()) {
                Toast.makeText(context, "아이디 비밀번호를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val params = SignInParams(email, password)
            viewModel.requestLogin(params)
        }
    }

    private fun subscribeIsLoginSuccess() {
        viewModel.isLoginSuccess.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess) {
                Navigation.findNavController(view!!).popBackStack()
                return@Observer
            }
            Toast.makeText(context, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
        })
    }

}
