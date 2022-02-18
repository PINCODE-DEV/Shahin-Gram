package com.example.shahingram.Home

import android.util.Log
import io.reactivex.rxjava3.schedulers.Schedulers

class Home_ViewModel {

    object STon {
        val instance = Home_ViewModel()
    }

    private val repo = Home_Repo.STon.instance

    fun getStories(onGetStories: OnGetStories){
        repo.getStories()
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onGetStories.onReceived(it)
                },
                {
                    onGetStories.onFailure()
                }
            )
    }

    fun getPosts(userId: String , onGetPostReceived: OnGetPostReceived) {
        repo.getPosts(userId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onGetPostReceived.onReceived(it)
                },
                {
                    onGetPostReceived.onFailure(it.message.toString())
                }
            )
    }

    fun likePost(userId: String, postId: String, onPostLiked: OnPostLiked) {
        repo.likePost(userId, postId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onPostLiked.onLiked()
                },
                {
                    onPostLiked.onFailure()
                }
            )
    }

    fun unLikePost(userId: String, postId: String, onPostUnLiked: OnPostUnLiked) {
        repo.unLikePost(userId, postId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onPostUnLiked.onUnLiked()
                },
                {
                    onPostUnLiked.onFailure()
                }
            )
    }

    fun savePost(userId: String, postId: String, onPostSaved: OnPostSaved) {
        repo.savePost(userId, postId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onPostSaved.onSaved(it.status.toString())
                },
                {
                    Log.i("LOG", "savePost: ${it.message}")
                }
            )
    }

    fun unSavePost(userId: String, postId: String, onPostUnSaved: OnPostUnSaved) {
        repo.unSavePost(userId, postId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onPostUnSaved.onUnSaved()
                },
                {
                    Log.i("LOG", "unSavePost: ${it.message}")
                }
            )
    }

    fun getPostComments(postId: String , onGetComments: OnGetComments){
        repo.getPostComments(postId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onGetComments.getComments(it)
                },
                {
                    onGetComments.onFailure()
                }
            )
    }

    fun getLikeCount(postId: String, onGetLikeCount: OnGetLikeCount) {
        repo.getLikeCount(postId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onGetLikeCount.getLikeCount(it.likeCount.toString())
                }, {}
            )
    }

    fun addComment(userId: String, postId: String, comment: String , onAddComment: OnAddComment){
        repo.addComment(userId, postId, comment)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onAddComment.added()
                },
                {
                    Log.i("LOG", "addComment: "+it.message.toString())
                }
            )
    }

    fun deletePost(postId : String , onDeletePost: OnDeletePost){
        repo.deletePost(postId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onDeletePost.onDelete(it.status.toString())
                },
                {
                    Log.i("LOG", "deletePost: ${it.message}")
                }
            )
    }

    interface OnGetStories{
        fun onReceived(stories : List<Story>)
        fun onFailure()
    }

    interface OnGetPostReceived {
        fun onReceived(models: List<Post>)
        fun onFailure(message : String)
    }

    interface OnPostLiked {
        fun onLiked()
        fun onFailure()
    }

    interface OnPostUnLiked {
        fun onUnLiked()
        fun onFailure()
    }

    interface OnPostSaved {
        fun onSaved(status : String)
    }

    interface OnPostUnSaved {
        fun onUnSaved()
    }

    interface OnGetComments{
        fun getComments(comments : List<Comment>)
        fun onFailure()
    }

    interface OnGetLikeCount {
        fun getLikeCount(likeCount: String)
    }

    interface OnAddComment{
        fun added()
    }

    interface OnDeletePost{
        fun onDelete(status : String)
    }
}