package com.ebookfrenzy.withmap.view.login


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.lifecycle.MutableLiveData
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentSignUpBinding
import com.ebookfrenzy.withmap.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {
//
//    private lateinit var binding : FragmentSignUpBinding

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//
//        binding = FragmentSignUpBinding.inflate(LayoutInflater.from(context))
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeatButtonListener()

    }
    fun repeatButtonListener() {
        et_email.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotEmpty()) {
                    btn_email_repeat
                        .setBackgroundResource(R.drawable.bg_button_repeat_focus)
                    if(isValidEmail(s)){
                        tv_frag_sign_up_email_form.visibility = View.GONE
                    }else
                        tv_frag_sign_up_email_form.visibility = View.VISIBLE
                }else{
                    btn_email_repeat.setBackgroundResource(R.drawable.bg_button_repeat_not_focus)
                }
            }
        })
        et_nickname.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotEmpty()) {
                    btn_nickname_repeat.setBackgroundResource(R.drawable.bg_button_repeat_focus)
                }else{
                    btn_nickname_repeat.setBackgroundResource(R.drawable.bg_button_repeat_not_focus)
                }
            }
        })
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()

    }

//    fun repeatButtonListener(i : Int) {
//        when(i) {
//            1 -> if(binding.etEmail.text.isNotEmpty()) {
//                binding.btnEmailRepeat.setBackgroundResource(R.drawable.bg_button_repeat_focus)
//            }else {
//                binding.btnEmailRepeat.setBackgroundResource(R.drawable.bg_button_repeat_not_focus)
//            }
//
//            2 -> if(binding.etNickname.text.isNotEmpty()) {
//                binding.btnNicknameRepeat.setBackgroundResource(R.drawable.bg_button_repeat_focus)
//            }else {
//                binding.btnNicknameRepeat.setBackgroundResource(R.drawable.bg_button_repeat_not_focus)
//            }
//        }
//    }
//
//    @BindingAdapter("is_repeat")
//    fun Button.setButton(isText : Boolean) {
//        if(isText) {
//            setBackgroundResource(R.drawable.bg_button_repeat_focus)
//        } else{
//            setBackgroundResource(R.drawable.bg_button_repeat_not_focus)
//        }
//    }
}
