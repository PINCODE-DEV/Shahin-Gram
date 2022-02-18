package com.example.shahingram.Home

import com.google.gson.annotations.SerializedName

data class Comment(

	@field:SerializedName("profile_img")
	val profileImg: String? = null,

	@field:SerializedName("comment")
	val comment: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
