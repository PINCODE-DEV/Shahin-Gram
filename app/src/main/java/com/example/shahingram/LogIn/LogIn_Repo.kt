package com.example.shahingram.LogIn

import io.reactivex.rxjava3.core.Single

class LogIn_Repo : LogIn_DataSource{

    object STon{
        val instance = LogIn_Repo()
    }

    val api = LogIn_Api.STon.instance
    override fun logIn(username: String, password: String): Single<LogIn> {
        return api.logIn(username,password)
    }
}