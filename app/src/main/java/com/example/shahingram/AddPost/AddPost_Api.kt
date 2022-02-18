package com.example.shahingram.AddPost

import com.example.shahingram.Retrofit.ApiProvider
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddPost_Api : AddPost_DataSource {
    object STon{
        val instance = AddPost_Api()
    }
    val apiService = ApiProvider.STon.instance
    override fun uploadPost(userId : RequestBody, image: MultipartBody.Part, content: RequestBody): Single<AddPost> {
        return apiService.uploadPost(userId,image,content)
    }

}