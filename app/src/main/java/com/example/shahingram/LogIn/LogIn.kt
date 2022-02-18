package com.example.shahingram.LogIn

import com.google.gson.annotations.SerializedName

data class LogIn(

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
