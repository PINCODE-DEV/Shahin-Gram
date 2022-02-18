package com.example.shahingram.Retrofit

import com.example.shahingram.AddPost.AddPost
import com.example.shahingram.Home.*
import com.example.shahingram.LogIn.LogIn
import com.example.shahingram.Profile.JsonOk
import com.example.shahingram.Profile.UserInfo
import com.example.shahingram.Profile.UserProfPrev
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import kotlin.random.Random

interface ApiService {
    @FormUrlEncoded
    @POST("log_in.php")
    fun logIn(@Field("username") username: String, @Field("pass") pass: String): Single<LogIn>

    @FormUrlEncoded
    @POST("sign_up.php")
    fun signUp(
        @Field("username") username: String,
        @Field("password") pass: String,
        @Field("email") email: String,
    ): Single<LogIn>

    @GET("get_stories.php")
    fun getStories(@Query("r") random: Int): Single<List<Story>>

    @FormUrlEncoded
    @POST("get_posts.php")
    fun getPosts(@Field("user_id") userId: String): Single<List<Post>>

    @FormUrlEncoded
    @POST("get_my_posts.php")
    fun getMyPosts(@Field("userId") userId: String): Single<List<Post>>

    @Multipart
    @POST("add_post_api.php")
    fun uploadPost(
        @Part("user_id") userId: RequestBody,
        @Part postImage: MultipartBody.Part,
        @Part("content") content: RequestBody,
    ): Single<AddPost>

    @FormUrlEncoded
    @POST("user_info.php")
    fun getUserInfo(
        @Field("username") username: String,
        @Field("my_id") myId: String,
    ): Single<UserInfo>

    @FormUrlEncoded
    @POST("update_profile.php")
    fun updateProfile(
        @Field("oldUsername") oldUsername: String,
        @Field("oldPass") oldPass: String,
        @Field("newUsername") newUsername: String,
        @Field("newPass") newPass: String,
        @Field("email") email: String,
        @Field("bio") bio: String,
    ): Single<JsonOk>

    @Multipart
    @POST("update_profile_image.php")
    fun updateProfileImage(
        @Part("username") username: RequestBody,
        @Part("pass") pass: RequestBody,
        @Part profileImage: MultipartBody.Part,
    ): Single<JsonOk>

    @FormUrlEncoded
    @POST("like_post.php")
    fun likePost(@Field("userId") userId: String, @Field("postId") postId: String): Single<JsonOk>

    @FormUrlEncoded
    @POST("un_like_post.php")
    fun unLikePost(@Field("userId") userId: String, @Field("postId") postId: String): Single<JsonOk>

    @FormUrlEncoded
    @POST("get_post_like_count.php")
    fun likeCount(@Field("postId") postId: String): Single<LikeCount>

    @FormUrlEncoded
    @POST("get_post_comments.php")
    fun getComments(@Field("post_id") postId: String): Single<List<Comment>>

    @FormUrlEncoded
    @POST("add_comment.php")
    fun addComment(
        @Field("user_id") userId: String,
        @Field("post_id") postId: String,
        @Field("comment") comment: String,
    ): Single<JsonStatus>

    @FormUrlEncoded
    @POST("get_my_followers.php")
    fun getFollowers(
        @Field("user_id") userId: String,
    ): Single<List<UserProfPrev>>

    @FormUrlEncoded
    @POST("delete_post.php")
    fun deletePost(@Field("post_id") postId: String): Single<JsonStatus>

    @FormUrlEncoded
    @POST("save_post.php")
    fun savePost(
        @Field("user_id") userId: String,
        @Field("post_id") postId: String,
    ): Single<JsonStatus>

    @FormUrlEncoded
    @POST("un_save_post.php")
    fun unSavePost(
        @Field("user_id") userId: String,
        @Field("post_id") postId: String,
    ): Single<JsonStatus>

    @FormUrlEncoded
    @POST("get_saved_posts.php")
    fun getSavedPosts(@Field("user_id") userId: String): Single<List<Post>>

    @FormUrlEncoded
    @POST("search_user.php")
    fun searchUsers(@Field("username") username: String): Single<List<UserProfPrev>>

    @FormUrlEncoded
    @POST("follow_user.php")
    fun followUser(
        @Field("my_id") myId: String,
        @Field("user_id") userId: String,
    ): Single<JsonStatus>

    @FormUrlEncoded
    @POST("un_follow_user.php")
    fun unFollowUser(
        @Field("my_id") myId: String,
        @Field("user_id") userId: String,
    ): Single<JsonStatus>


}