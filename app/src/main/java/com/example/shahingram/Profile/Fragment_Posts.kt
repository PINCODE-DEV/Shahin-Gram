package com.example.shahingram.Profile

import DoubleClickListener
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.daimajia.androidanimations.library.Techniques
import com.example.shahingram.*
import com.example.shahingram.Home.*
import com.example.shahingram.Retrofit.ApiClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.view.*

class Fragment_Posts() : Fragment() {
    private lateinit var mView: View
    private val viewModel = Home_ViewModel()
    private lateinit var prefInfo: SharedPreferences
    private lateinit var post: Post
    var userId = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mView = inflater.inflate(R.layout.fragment_post, container, false)
        prefInfo = requireContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        userId = prefInfo.getString("userId", "").toString()

        SetupViews()

        mView.img_postFragment_more.setOnClickListener {
            ShowBottomSheet(post)
        }

        return mView
    }

    @SuppressLint("SetTextI18n")
    private fun SetupViews() {
        MainActivity.currentFrag = "Posts"
        HideOrShowBottomNav("Hide")
        post = arguments?.getParcelable<Parcelable>("post") as Post
        mView.txt_postFragment_username.text = post.username
        LoadImageProfile("${ApiClient().baseUrl}${post.profile}",mView.img_postFragment_prof)
        LoadImage("${ApiClient().baseUrl}${post.image}",mView.img_postFragment_post)
        mView.txt_postFragment_likeCount.text = post.likeCount + " Likes"
        mView.txt_postFragment_desc.text = post.description

        if (post.iLiked.equals("0")) {
            (context as MainActivity).runOnUiThread(Runnable {
                mView.img_postFragment_like.setImageResource(R.drawable.ic_un_like)
            })
        } else {
            (context as MainActivity).runOnUiThread(Runnable {
                mView.img_postFragment_like.setImageResource(R.drawable.ic_like)
            })
        }

        if (post.iSaved.equals("0")) {
            (context as MainActivity).runOnUiThread(Runnable {
                mView.img_postFragment_save.setImageResource(R.drawable.ic_un_save)
            })
        } else {
            (context as MainActivity).runOnUiThread(Runnable {
                mView.img_postFragment_save.setImageResource(R.drawable.ic_save)
            })
        }

        mView.txt_postFragment_likeCount.text = post.likeCount + " Liked"

        mView.img_postFragment_post.setOnClickListener(
            DoubleClickListener(
                callback = object : DoubleClickListener.Callback {
                    override fun doubleClicked() {
                        LikePost(post)
                    }
                }
            )
        )

        mView.img_postFragment_like.setOnClickListener {
            LikePost(post)
        }

        mView.img_postFragment_save.setOnClickListener {
            SavePost(it, post)
        }

        mView.img_postFragment_back.setOnClickListener {
            findNavController().popBackStack()
        }

        mView.img_postFragment_comment.setOnClickListener {
            val myComment = MyCommentModel(post.id.toString(), post.description.toString())
            val nextAction =
                Fragment_PostsDirections.actionFragmentPostsToFragmentComments(
                    myComment)
            Navigation.findNavController(it).navigate(nextAction)
        }

    }

    private fun LikePost(model: Post) {
        if (model.iLiked.equals("0")) {
            SetYoYo(Techniques.Tada, 500, mView.img_postFragment_like)
            img_postFragment_like.setImageResource(R.drawable.ic_like)
            viewModel.likePost(userId,
                model.id!!,
                object : Home_ViewModel.OnPostLiked {
                    override fun onLiked() {
                        viewModel.getLikeCount(model.id,
                            object : Home_ViewModel.OnGetLikeCount {
                                override fun getLikeCount(likeCount: String) {
                                    (context as MainActivity).runOnUiThread {
                                        txt_postFragment_likeCount.text = likeCount + " likes"
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
            SetYoYo(Techniques.Tada, 500, mView.img_postFragment_like)
            img_postFragment_like.setImageResource(R.drawable.ic_un_like)
            viewModel.unLikePost(userId,
                model.id!!,
                object : Home_ViewModel.OnPostUnLiked {
                    override fun onUnLiked() {
                        viewModel.getLikeCount(model.id,
                            object : Home_ViewModel.OnGetLikeCount {
                                override fun getLikeCount(likeCount: String) {
                                    (context as MainActivity).runOnUiThread {
                                        txt_postFragment_likeCount.text = likeCount + " likes"
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

    private fun SavePost(view: View, model: Post) {
        if (model.iSaved.equals("0")) {
            SetYoYo(Techniques.Swing, 500, view.img_postFragment_save)
            view.img_postFragment_save.setImageResource(R.drawable.ic_save)
            viewModel.savePost(userId,
                model.id!!,
                object : Home_ViewModel.OnPostSaved {
                    override fun onSaved(status: String) {
                        if (status == "success") {
                            requireActivity().runOnUiThread {
                                Toast.makeText(context, "This Post Saved!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            model.iSaved = "1"
                        } else {
                            requireActivity().runOnUiThread {
                                Toast.makeText(context, "This Post Exist!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                })
        } else {
            SetYoYo(Techniques.Swing, 500, view.img_postFragment_save)
            view.img_postFragment_save.setImageResource(R.drawable.ic_un_save)
            viewModel.unSavePost(userId,
                model.id!!,
                object : Home_ViewModel.OnPostUnSaved {
                    override fun onUnSaved() {
                        requireActivity().runOnUiThread {
                            Toast.makeText(context, "This Post Un Saved!", Toast.LENGTH_SHORT)
                                .show()
                            model.iSaved = "0"
                        }
                    }
                })
        }
    }

    private fun ShowBottomSheet(model: Post) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
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
                }.setNeutralButton("Cancel") { dialogInterface, which -> }
                .show()
        }
        bottomSheetDialog.show()
    }

    private fun DeletePost(id: String) {
        viewModel.deletePost(id, object : Home_ViewModel.OnDeletePost {
            override fun onDelete(status: String) {
                if (status == "success")
                    requireActivity().runOnUiThread {
                        findNavController().popBackStack()
                    }
            }
        })
    }
}