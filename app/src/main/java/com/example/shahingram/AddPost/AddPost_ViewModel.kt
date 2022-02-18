package com.example.shahingram.AddPost

import android.util.Log
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddPost_ViewModel {

    object STon {
        val instance = AddPost_ViewModel()
    }

    val repo = AddPost_Repo.STon.instance

    fun uploadPost(userId : RequestBody, image: MultipartBody.Part, content: RequestBody, onPostUpload: OnPostUpload) {
        repo.uploadPost(userId, image, content)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onPostUpload.upload(it.state!!)
                },
                {
                    Log.i("LOG", "uploadPost: "+it.message)
                    onPostUpload.failure()
                }
            )
    }

    interface OnPostUpload {
        fun upload(status: String)
        fun failure()
    }
}