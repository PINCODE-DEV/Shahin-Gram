package com.example.shahingram.AddPost

import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddPost_Repo : AddPost_DataSource {

    object STon{
        val instance = AddPost_Repo()
    }

    val api = AddPost_Api.STon.instance
    override fun uploadPost(userId : RequestBody, image: MultipartBody.Part, content: RequestBody): Single<AddPost> {
        return api.uploadPost(userId, image, content)
    }

}