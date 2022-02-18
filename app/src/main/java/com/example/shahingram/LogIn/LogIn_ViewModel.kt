package com.example.shahingram.LogIn

import android.util.Log
import io.reactivex.rxjava3.schedulers.Schedulers

open class LogIn_ViewModel {

    object STon{
        val instance = LogIn_ViewModel()
    }

    private val repo = LogIn_Repo.STon.instance

    fun logIn(username: String, pass: String, onLogInResPonce: OnLogInResponse) {
        repo.logIn(username, pass)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onLogInResPonce.onResponse(it)
                }, {
                    onLogInResPonce.onFailure()
                    Log.i("LOG", "logIn: ${it.message}")
                })
    }

    interface OnLogInResponse {
        fun onResponse(model : LogIn)
        fun onFailure()
    }
}