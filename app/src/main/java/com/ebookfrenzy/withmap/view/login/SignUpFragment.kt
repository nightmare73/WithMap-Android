package com.ebookfrenzy.withmap.view.login


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentSignUpBinding
import com.ebookfrenzy.withmap.network.request.SignUpParams
import com.ebookfrenzy.withmap.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private var isEmailUnique = false
    private var isNicknameUnique = false

    private val viewModelFactory: ViewModelProvider.Factory by inject()
    private lateinit var viewModel: SignUpViewModel

    private val emailEditTextListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val isEmail = Patterns.EMAIL_ADDRESS.matcher(s).matches()
            if (isEmail && s!!.isNotBlank()) {
                tv_frag_sign_up_email_form.visibility = View.GONE
                btn_email_repeat.isEnabled = true
                return
            }
            tv_frag_sign_up_email_form.visibility = View.VISIBLE
            btn_email_repeat.isEnabled = false
        }
    }

    private val nicknameEditTextListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s!!.isNotBlank()) {
                btn_nickname_repeat.isEnabled = true
                return
            }
            btn_nickname_repeat.isEnabled = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[SignUpViewModel::class.java]

        val binding = FragmentSignUpBinding.inflate(inflater)
        binding.lifecycleOwner = this

        initView(binding)
        subscribeIsEmailUnique()
        subscribeIsNicknameUnique()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeatButtonListener()
    }

    private fun repeatButtonListener() {
        et_email.addTextChangedListener(emailEditTextListener)
        et_nickname.addTextChangedListener(nicknameEditTextListener)
    }


    private fun initView(binding: FragmentSignUpBinding) {
        binding.btnSignUp.setOnClickListener {
            if (isEmailUnique && isNicknameUnique) {
                val params = getSignUpParams(binding)
                val dest = SignUpFragmentDirections.actionSignUpFragmentToSignUpInfoFragment(params)
                Navigation.findNavController(it).navigate(dest)
                return@setOnClickListener
            }
            Toast.makeText(context, "중복확인을 하지 않은 항목이 있습니다.", Toast.LENGTH_SHORT).show()
        }

        binding.btnEmailRepeat.setOnClickListener {

            val email = binding.etEmail.text.toString()
            viewModel.checkEmailIsUnique(email)
            Log.d("Malibin Debug", "email : $email")
        }

        binding.btnNicknameRepeat.setOnClickListener {
            val nickname = binding.etNickname.text.toString()
            viewModel.checkNicknameIsUnique(nickname)
        }
    }

    private fun subscribeIsEmailUnique() {
        viewModel.isEmailUnique.observe(viewLifecycleOwner, Observer { isUnique ->
            isEmailUnique = isUnique
            if (isUnique) {
                Toast.makeText(context, "중복되지 않는 이메일입니다.", Toast.LENGTH_SHORT).show()
                return@Observer
            }
            Toast.makeText(context, "중복된 이메일입니다.", Toast.LENGTH_SHORT).show()
        })
    }

    private fun subscribeIsNicknameUnique() {
        viewModel.isNicknameUnique.observe(viewLifecycleOwner, Observer { isUnique ->
            isNicknameUnique = isUnique
            if (isUnique) {
                Toast.makeText(context, "중복되지 않는 닉네임입니다.", Toast.LENGTH_SHORT).show()
                return@Observer
            }
            Toast.makeText(context, "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show()
        })
    }

    private fun getSignUpParams(binding: FragmentSignUpBinding): SignUpParams {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val nickname = binding.etNickname.text.toString()
        return SignUpParams(email, nickname, password)
    }

}
