package com.example.shahingram.Home

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Story(

	@field:SerializedName("profile")
	val profile: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("story")
	val story: String? = null

) : Parcelable {
	constructor(parcel: Parcel) : this(
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

	companion object CREATOR : Parcelable.Creator<Story> {
		override fun createFromParcel(parcel: Parcel): Story {
			return Story(parcel)
		}

		override fun newArray(size: Int): Array<Story?> {
			return arrayOfNulls(size)
		}
	}
}
