package com.example.shahingram.Home

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Post(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("like_count")
	val likeCount: String? = null,

	@field:SerializedName("i_liked")
    var iLiked: String? = null,

	@field:SerializedName("i_saved")
	var iSaved: String? = null,

	@field:SerializedName("profile")
	val profile: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("username")
	val username: String? = null


) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString()) {
	}

	override fun describeContents(): Int {
		TODO("Not yet implemented")
	}

	override fun writeToParcel(p0: Parcel?, p1: Int) {
		TODO("Not yet implemented")
	}

	companion object CREATOR : Parcelable.Creator<Post> {
		override fun createFromParcel(parcel: Parcel): Post {
			return Post(parcel)
		}

		override fun newArray(size: Int): Array<Post?> {
			return arrayOfNulls(size)
		}
	}
}
