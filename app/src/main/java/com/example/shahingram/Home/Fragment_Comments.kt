package com.example.shahingram.Home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.shahingram.HideOrShowBottomNav
import com.example.shahingram.MainActivity
import com.example.shahingram.R
import com.example.shahingram.SetYoYo
import kotlinx.android.synthetic.main.fragment_comments.view.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import kotlinx.android.synthetic.main.posts_item.view.*
import java.util.*

class Fragment_Comments : Fragment() {
    private lateinit var mView: View
    private lateinit var myComment: MyCommentModel
    private lateinit var prefInfo : SharedPreferences
    private var userId = ""
    private val viewModel = Home_ViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mView = inflater.inflate(R.layout.fragment_comments, container, false)
        prefInfo = requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        userId = prefInfo.getString("userId", "")!!
        HideOrShowBottomNav("Hide")
        SetupViews()
        return mView;
    }

    private fun SetupViews() {
        MainActivity.currentFrag = "Comments"
        myComment = arguments?.getParcelable<Parcelable>("my_comment") as MyCommentModel

        mView.txt_commentsFragment_desc.text = myComment.description

        SetYoYo(Techniques.FlipInX, 2000, mView.txt_commentsFragment_desc)
        SetYoYo(Techniques.Shake, 1500, mView.edt_commentFragment_comment)

        GetPostComments()

        mView.edt_commentFragment_comment.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = mView.edt_commentFragment_comment.text.toString().length
                if (length == 1 && before < length) {
                    SetYoYo(Techniques.FadeInUp, 500, mView.btn_commentFragment_addComment)
                    mView.btn_commentFragment_addComment.visibility = View.VISIBLE
                } else if (length == 0){
                    SetYoYo(Techniques.FadeOutUp, 500, mView.btn_commentFragment_addComment)
                    val timer = object : CountDownTimer(500, 500) {
                        override fun onTick(millisUntilFinished: Long) {}
                        override fun onFinish() {
                            mView.btn_commentFragment_addComment.visibility = View.INVISIBLE
                        }
                    }
                    timer.start()
                }
            }
        })

        mView.btn_commentFragment_addComment.setOnClickListener {
            if (mView.edt_commentFragment_comment.text.toString().isNotEmpty()) {
                AddComment(userId,
                    myComment.postId.toString(),
                    mView.edt_commentFragment_comment.text.toString())
                mView.edt_commentFragment_comment.text.clear()
            }
        }
        mView.img_commentsFragment_back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun AddComment(userId: String, postId: String, comment: String) {
        viewModel.addComment(userId, postId, comment, object : Home_ViewModel.OnAddComment {
            override fun added() {
                GetPostComments()
            }
        })
    }

    private fun GetPostComments(){
        viewModel.getPostComments(myComment.postId.toString(),
            object : Home_ViewModel.OnGetComments {
                override fun getComments(comments: List<Comment>) {
                    requireActivity().runOnUiThread {
                        mView.rv_commentsFragment_comments.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false)
                        mView.rv_commentsFragment_comments.adapter =
                            Rv_Comments_Adapter(requireContext(), comments)
                    }
                }

                override fun onFailure() {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(),
                            "خطا در ارتباط با سرور",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
}