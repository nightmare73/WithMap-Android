package com.ebookfrenzy.withmap.viewmodel

import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.network.WithMapService
import com.ebookfrenzy.withmap.network.request.SignInParams
import com.ebookfrenzy.withmap.respository.LocalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class LoginViewModel(
    private val withMapService: WithMapService,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean>
        get() = _isLoginSuccess

    val inputEmail = ObservableField<String>()

    val inputPassword = ObservableField<String>()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun requestLogin(params: SignInParams) {
        addDisposable(
            withMapService.requestSignIn(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _isLoginSuccess.value = true
                    Log.d("Malibin Debug", "request login response token : ${it.token}")
                    saveToken(it.token)
                }, {
                    _isLoginSuccess.value = false
                    showError(it)
                })
        )
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun showError(it: Throwable) {
        Log.d("Malibin Debug", "response : ${TextUtils.join("\n", it.stackTrace)}")
        Log.d("Malibin Debug", "response : ${it.message}")
        Log.d(
            "Malibin Debug",
            "errorBody: ${(it as HttpException).response().errorBody()?.string()}"
        )
    }

    private fun saveToken(token: String) {
        localRepository.saveAuthToken(token)
        Log.d("Malibin Debug", "save 직후 token : ${localRepository.getAuthToken()}")
    }

}
//
//@BindingAdapter(value = ["textFrom", "etEmailOrNickname"])
//fun etIsText(
//    view: Button,
//    textFrom: String,
//    etEmailOrNickname: Int
//) {
//    when (etEmailOrNickname) {
//        1 -> if (textFrom.isNotEmpty()) {
//            view.setBackgroundResource(R.drawable.bg_button_repeat_focus)
//        } else {
//            view.setBackgroundResource(R.drawable.bg_button_repeat_not_focus)
//        }
//    }
//}