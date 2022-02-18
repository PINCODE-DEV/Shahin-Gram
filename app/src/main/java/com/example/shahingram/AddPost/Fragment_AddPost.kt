package com.example.shahingram.AddPost

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.daimajia.androidanimations.library.Techniques
import com.example.shahingram.MainActivity
import com.example.shahingram.R
import com.example.shahingram.Search.Fragment_SearchDirections
import com.example.shahingram.SetYoYo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_add_post.*
import kotlinx.android.synthetic.main.fragment_add_post.view.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class Fragment_AddPost() : Fragment() {
    companion object {
        private const val SELECT_IMAGE_CODE = 1001
    }

    private var userId = ""
    private var data: Uri? = null

    private lateinit var viewModel: AddPost_ViewModel
    private lateinit var myView: View
    private lateinit var imgSelect: ImageView
    private lateinit var rlImage: RelativeLayout
    private lateinit var rlDelete: RelativeLayout
    private lateinit var image: ImageView
    private lateinit var edtDesc: EditText
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var prefInfo: SharedPreferences
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        myView = inflater.inflate(R.layout.fragment_add_post, container, false)
        viewModel = AddPost_ViewModel.STon.instance
        prefInfo = container!!.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        if (prefInfo != null)
            userId = prefInfo.getString("userId", "")!!


        SetupViews()
        Listeners()

        return myView
    }

    private fun Listeners() {
        imgSelect.setOnClickListener {
            SelectImage(SELECT_IMAGE_CODE)
        }

        rlDelete.setOnClickListener {
            image.setImageResource(0)
            imgSelect.visibility = View.VISIBLE
            txt_addPostFragment_select.visibility = View.VISIBLE
            rlImage.visibility = View.INVISIBLE
            btnAdd.visibility = View.INVISIBLE
            edtDesc.text.clear()
            edtDesc.visibility = View.INVISIBLE
        }

        btnAdd.setOnClickListener {
            if (edtDesc.text.toString().isNotEmpty()) {
                progress_addPostFragment_progress.visibility = View.VISIBLE
                UploadPost(data!!)

            } else {
                Toast.makeText(context, "توضیحات پست خالی است!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun UploadPost(data: Uri) {
        val upload = UriToUploadable(requireActivity())
        val postImage = upload.getUploaderFile(data, "pImage", "${UUID.randomUUID()}")

        val rBUserId = RequestBody.create(okhttp3.MultipartBody.FORM,userId)
        val rBContent = RequestBody.create(okhttp3.MultipartBody.FORM,edtDesc.text.toString())

        viewModel.uploadPost(rBUserId,
            postImage,
            rBContent,
            object : AddPost_ViewModel.OnPostUpload {
                override fun upload(status: String) {
                    requireActivity().runOnUiThread(Runnable {
                        progress_addPostFragment_progress.visibility = View.GONE
                        if (status.equals("success")) {
                            Toast.makeText(context, "آپلود موفق بود!", Toast.LENGTH_SHORT).show()
                            val nextAction =
                                Fragment_AddPostDirections.actionFragmentAddPostToFragmentHome()
                            navController.navigate(nextAction)
                            MainActivity.bottomNav.setSelected(0)
                        } else {
                            Toast.makeText(context, "آپلود ناموفق بود!", Toast.LENGTH_SHORT).show()
                        }
                    })

                }

                override fun failure() {
                    requireActivity().runOnUiThread(Runnable {
                        Toast.makeText(context, "خطا در ارتباط با سرور!", Toast.LENGTH_SHORT).show()
                    })
                }

            })
    }

    private fun SetupViews() {
        imgSelect = myView.findViewById(R.id.img_addPostFragment_select)
        rlImage = myView.findViewById(R.id.rl_addPostFragment_post)
        rlDelete = myView.findViewById(R.id.rl_addPostFragment_delete)
        image = myView.findViewById(R.id.img_addPostFragment_Image)
        btnAdd = myView.findViewById(R.id.btn_addPostFragment_add)

        edtDesc = myView.findViewById(R.id.edt_addPostFragment_desc)
        btnAdd = myView.findViewById(R.id.btn_addPostFragment_add)


        SetYoYo(Techniques.ZoomIn,800,imgSelect)
        SetYoYo(Techniques.ZoomIn,800,myView.txt_addPostFragment_select)
        imgSelect.visibility = View.VISIBLE
        myView.txt_addPostFragment_select.visibility = View.VISIBLE
    }

    private fun SelectImage(requestCode: Int) {
        val gallery = Intent(Intent.ACTION_GET_CONTENT)
        gallery.type = "image/*"
        startActivityForResult(gallery, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_IMAGE_CODE && resultCode == Activity.RESULT_OK && data != null) {
            image.setImageURI(data.data)
            imgSelect.visibility = View.INVISIBLE
            txt_addPostFragment_select.visibility = View.INVISIBLE
            rlImage.visibility = View.VISIBLE
            edtDesc.visibility = View.VISIBLE
            btnAdd.visibility = View.VISIBLE

            this.data = data.data

        }
    }
}