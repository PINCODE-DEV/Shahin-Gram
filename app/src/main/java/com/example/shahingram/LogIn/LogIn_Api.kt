package com.example.shahingram.LogIn

import com.example.shahingram.Retrofit.ApiProvider
import io.reactivex.rxjava3.core.Single

class LogIn_Api : LogIn_DataSource {

    object STon{
        val instance = LogIn_Api()
    }

    val apiService = ApiProvider.STon.instance
    override fun logIn(username: String, password: String): Single<LogIn> {
        return apiService.logIn(username,password)
    }
}