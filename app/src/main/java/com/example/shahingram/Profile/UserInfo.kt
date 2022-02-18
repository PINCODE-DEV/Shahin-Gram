package com.example.shahingram.Profile

import com.google.gson.annotations.SerializedName

data class UserInfo(

	@field:SerializedName("profile_img")
	val profileImg: String? = null,

	@field:SerializedName("following_count")
	val followingCount: String? = null,

	@field:SerializedName("followers_count")
	val followersCount: String? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("post_count")
	val postCount: String? = null,

	@field:SerializedName("i_followed")
	var iFollowed: String? = null
)
