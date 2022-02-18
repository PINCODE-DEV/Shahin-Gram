package com.example.shahingram.SignUp

import com.example.shahingram.LogIn.LogIn
import com.example.shahingram.Retrofit.ApiProvider
import io.reactivex.rxjava3.core.Single

class SignUp_Api : SignUp_DataSource{

    object STon{
        val instance = SignUp_Api()
    }

    val apiService = ApiProvider.STon.instance
    override fun signUp(username: String, pass: String, email: String): Single<LogIn> {
        return apiService.signUp(username, pass, email)
    }
}