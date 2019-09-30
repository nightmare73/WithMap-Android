package com.ebookfrenzy.withmap.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.network.WithMapService
import com.ebookfrenzy.withmap.network.request.SignUpParams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

/**
 * Created By Yun Hyeok
 * on 9월 29, 2019
 */

class SignUpViewModel(private val withMapService: WithMapService) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _isEmailUnique = MutableLiveData<Boolean>()
    val isEmailUnique: LiveData<Boolean>
        get() = _isEmailUnique

    private val _isNicknameUnique = MutableLiveData<Boolean>()
    val isNicknameUnique: LiveData<Boolean>
        get() = _isNicknameUnique

    private val _isSignUpSuccess = MutableLiveData<Boolean>()
    val isSignUpSuccess: LiveData<Boolean>
        get() = _isSignUpSuccess

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun checkEmailIsUnique(email: String) {
        addDisposable(
            withMapService.checkDuplicated("email", email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _isEmailUnique.value = !it.overlapped
                }, {
                    showError(it)
                })
        )
    }

    fun checkNicknameIsUnique(nickname: String) {
        addDisposable(
            withMapService.checkDuplicated("name", nickname)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _isNicknameUnique.value = !it.overlapped
                }, {
                    showError(it)
                })
        )
    }

    fun requestSignUp(params: SignUpParams) {
        addDisposable(
            withMapService.requestSignUp(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _isSignUpSuccess.value = true
                }, {
                    _isSignUpSuccess.value = false
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
}