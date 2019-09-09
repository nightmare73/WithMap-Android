package com.ebookfrenzy.withmap.viewmodel

import android.widget.Button
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.R

class LoginViewModel (mEtEmailText : Boolean = false, mEtNicknameText : Boolean = false){

    val etEmailText = ObservableField<String>()
    val etNicknameText = ObservableField<String>()

}

@BindingAdapter(value=["textFrom", "etEmailOrNickname"])
fun etIsText(
    view : Button,
    textFrom : String,
    etEmailOrNickname : Int
){
    when(etEmailOrNickname) {
        1 -> if(textFrom.isNotEmpty()) {
            view.setBackgroundResource(R.drawable.bg_button_repeat_focus)
        } else{
            view.setBackgroundResource(R.drawable.bg_button_repeat_not_focus)
        }
    }
}