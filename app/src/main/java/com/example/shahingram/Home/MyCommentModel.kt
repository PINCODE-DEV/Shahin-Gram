package com.example.shahingram.Home

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MyCommentModel(
    @SerializedName("postId")
    val postId: String? = null,
    @SerializedName("description")
    val description: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<MyCommentModel> {
        override fun createFromParcel(parcel: Parcel): MyCommentModel {
            return MyCommentModel(parcel)
        }

        override fun newArray(size: Int): Array<MyCommentModel?> {
            return arrayOfNulls(size)
        }
    }
}
