package com.example.shahingram.SignUp

import com.example.shahingram.LogIn.LogIn
import io.reactivex.rxjava3.core.Single

class SignUp_Repo : SignUp_DataSource{

    object STon{
        val instance = SignUp_Repo()
    }

    val api = SignUp_Api.STon.instance
    override fun signUp(username: String, pass: String, email: String): Single<LogIn> {
        return api.signUp(username, pass, email)
    }
}