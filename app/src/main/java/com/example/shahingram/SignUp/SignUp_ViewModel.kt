package com.example.shahingram.SignUp

import android.util.Log
import com.example.shahingram.LogIn.LogIn
import io.reactivex.rxjava3.schedulers.Schedulers

class SignUp_ViewModel {

    object STon{
        val instance = SignUp_ViewModel()
    }

    val repo = SignUp_Repo.STon.instance
    fun signUp(username: String, pass: String, email: String , onSignUpResponse: OnSignUpResponse) {
        repo.signUp(username, pass, email)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                onSignUpResponse.onResponse(it)
                }, {
                    onSignUpResponse.onFailure()
                    Log.i("LOG", "signUp: "+it.message)
                }
            )
    }
    interface OnSignUpResponse{
        fun onResponse(model : LogIn)
        fun onFailure()
    }
}