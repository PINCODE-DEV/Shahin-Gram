package com.example.shahingram.SignUp

import com.example.shahingram.LogIn.LogIn
import io.reactivex.rxjava3.core.Single

interface SignUp_DataSource {
    fun signUp(username : String , pass : String , email : String) : Single<LogIn>
}