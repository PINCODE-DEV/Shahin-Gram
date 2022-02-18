package com.example.shahingram.Home

import DoubleClickListener
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.example.shahingram.*
import com.example.shahingram.LoadImageProfile
import com.example.shahingram.Retrofit.ApiClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.posts_item.view.*


class Rv_Posts_Adapter(
    val context: Activity,
    val posts: List<Post>,
    val onCommentClick: OnCommentClick,
    val onPostsUpdate: OnPostsUpdate,
) :
    RecyclerView.Adapter<PublicViewHolder>() {
    val viewModel = Home_ViewModel()
    private lateinit var prefInfo: SharedPreferences
    var userId = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicViewHolder {
        return PublicViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.posts_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PublicViewHolder, position: Int) {
        val view = holder.itemView

        prefInfo = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        userId = prefInfo.getString("userId", "").toString()


        val model = posts[position]
        view.txt_postItem_username.text = model.username
        view.txt_postItem_desc.text = model.description
        view.txt_postItem_likeCount.text = model.likeCount + " Likes"

        LoadImageProfile("${ApiClient().baseUrl}${model.profile}",view.img_postItem_prof)
        LoadImage("${ApiClient().baseUrl}${model.image}",view.img_postItem_post)

        if (model.iLiked.equals("0")) {
            (context).runOnUiThread(Runnable {
                view.img_postItem_like.setImageResource(R.drawable.ic_un_like)
            })
        } else {
            (context).runOnUiThread(Runnable {
                view.img_postItem_like.setImageResource(R.drawable.ic_like)
            })
        }

        if (model.iSaved.equals("0")) {
            (context).runOnUiThread(Runnable {
                view.img_postItem_save.setImageResource(R.drawable.ic_un_save)
            })
        } else {
            (context).runOnUiThread(Runnable {
                view.img_postItem_save.setImageResource(R.drawable.ic_save)
            })
        }

        holder.itemView.img_postItem_post.setOnClickListener(
            DoubleClickListener(
                callback = object : DoubleClickListener.Callback {
                    override fun doubleClicked() {
                        LikePost(view, model)
                    }
                }
            )
        )

        holder.itemView.img_postItem_comment.setOnClickListener {
            onCommentClick.onClick(it,
                MyCommentModel(model.id.toString(), model.description.toString()))
        }

        view.img_postItem_like.setOnClickListener {
            LikePost(view, model)
        }

        view.img_postItem_save.setOnClickListener {
            SavePost(view, model)
        }

        view.img_postItem_more.setOnClickListener {
            ShowBottomSheet(model)
        }

        val animation = AnimationUtils.loadAnimation(context, R.anim.anim_posts)
        view.rl_postItem_parent.startAnimation(animation)

    }

    private fun SavePost(view: View, model: Post) {
        if (model.iSaved.equals("0")) {
            SetYoYo(Techniques.Swing, 500, view.img_postItem_save)
            view.img_postItem_save.setImageResource(R.drawable.ic_save)
            viewModel.savePost(userId,
                model.id!!,
                object : Home_ViewModel.OnPostSaved {
                    override fun onSaved(status: String) {
                        if (status == "success"){
                            context.runOnUiThread {
                                Toast.makeText(context, "This Post Saved!", Toast.LENGTH_SHORT).show()
                                model.iSaved = "1"
                            }
                        }else if (status == "exist"){
                            context.runOnUiThread {
                                Toast.makeText(context, "This Post Exist!", Toast.LENGTH_SHORT).show()
                                model.iSaved = "1"
                            }
                        }
                    }
                })
        } else {
            SetYoYo(Techniques.Swing, 500, view.img_postItem_save)
            view.img_postItem_save.setImageResource(R.drawable.ic_un_save)
            viewModel.unSavePost(userId,
                model.id!!,
                object : Home_ViewModel.OnPostUnSaved {
                    override fun onUnSaved() {
                        context.runOnUiThread {
                            Toast.makeText(context, "This Post Un Saved!", Toast.LENGTH_SHORT).show()
                            model.iSaved = "0"
                        }
                    }
                })
        }
    }

    private fun ShowBottomSheet(model: Post) {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_post)

        val edit = bottomSheetDialog.findViewById<LinearLayout>(R.id.ll_bottomSheetPost_edit)
        val delete = bottomSheetDialog.findViewById<LinearLayout>(R.id.ll_bottomSheetPost_delete)

        edit?.setOnClickListener {
            Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show()
        }

        delete?.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Remove Post!")
                .setMessage("Are you sure you want to delete this post?")
                .setPositiveButton("Yes") { dialogInterface, which ->
                    DeletePost(model.id.toString())
                    bottomSheetDialog.dismiss()
                }
                .setNeutralButton("Cancel") { dialogInterface, which -> }
                .show()
        }
        bottomSheetDialog.show()
    }

    private fun DeletePost(id: String) {
        viewModel.deletePost(id, object : Home_ViewModel.OnDeletePost {
            override fun onDelete(status: String) {
                if (status == "success")
                    onPostsUpdate.onUpdate()
            }
        })
    }

    private fun LikePost(view: View, model: Post) {
        if (model.iLiked.equals("0")) {
            SetYoYo(Techniques.Tada, 500, view.img_postItem_like)
            view.img_postItem_like.setImageResource(R.drawable.ic_like)
            viewModel.likePost(userId,
                model.id!!,
                object : Home_ViewModel.OnPostLiked {
                    override fun onLiked() {
                        viewModel.getLikeCount(model.id,
                            object : Home_ViewModel.OnGetLikeCount {
                                override fun getLikeCount(likeCount: String) {
                                    (context as MainActivity).runOnUiThread {
                                        view.txt_postItem_likeCount.setText(likeCount + " likes")
                                        model.iLiked = "1"
                                    }
                                }
                            })
                    }
                    override fun onFailure() {
                        TODO("Not yet implemented")
                    }
                })
        } else {
            SetYoYo(Techniques.Tada, 500, view.img_postItem_like)
            view.img_postItem_like.setImageResource(R.drawable.ic_un_like)
            viewModel.unLikePost(userId,
                model.id!!,
                object : Home_ViewModel.OnPostUnLiked {
                    override fun onUnLiked() {
                        viewModel.getLikeCount(model.id,
                            object : Home_ViewModel.OnGetLikeCount {
                                override fun getLikeCount(likeCount: String) {
                                    (context as MainActivity).runOnUiThread {
                                        view.txt_postItem_likeCount.setText(likeCount + " likes")
                                        model.iLiked = "0"
                                    }
                                }
                            })
                    }

                    override fun onFailure() {
                        TODO("Not yet implemented")
                    }
                })
        }
    }

    override fun getItemCount(): Int = posts.size

    interface OnCommentClick {
        fun onClick(view: View, myComment: MyCommentModel)
    }

    interface OnPostsUpdate {
        fun onUpdate()
    }

}