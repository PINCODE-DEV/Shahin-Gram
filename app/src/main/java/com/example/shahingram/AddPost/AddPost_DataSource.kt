package com.example.shahingram.AddPost

import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface AddPost_DataSource {
    fun uploadPost(userId : RequestBody, image : MultipartBody.Part, content : RequestBody) : Single<AddPost>
}