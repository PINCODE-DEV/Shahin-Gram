package com.example.shahingram.LogIn

import io.reactivex.rxjava3.core.Single

interface LogIn_DataSource {
    fun logIn(username : String , password : String) : Single<LogIn>
}