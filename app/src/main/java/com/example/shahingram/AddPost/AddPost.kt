package com.example.shahingram.AddPost

import com.google.gson.annotations.SerializedName

data class AddPost(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("state")
	val state: String? = null
)
