package com.example.shahingram.Profile

import android.os.Parcel
import android.os.Parcelable
import com.example.shahingram.Home.Post
import com.google.gson.annotations.SerializedName

data class UserProfPrev(

    @field:SerializedName("profile")
    val profile: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
    }

    companion object CREATOR : Parcelable.Creator<UserProfPrev> {
        override fun createFromParcel(parcel: Parcel): UserProfPrev {
            return UserProfPrev(parcel)
        }

        override fun newArray(size: Int): Array<UserProfPrev?> {
            return arrayOfNulls(size)
        }
    }
}
