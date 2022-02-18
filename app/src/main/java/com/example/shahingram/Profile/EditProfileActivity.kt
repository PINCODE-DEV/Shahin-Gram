package com.example.shahingram.Profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import com.example.shahingram.AddPost.UriToUploadable
import com.example.shahingram.LoadImage
import com.example.shahingram.LoadImageProfile
import com.example.shahingram.R
import com.example.shahingram.Retrofit.ApiClient
import com.google.android.material.textfield.TextInputLayout
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_add_post.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    companion object {
        private const val SELECT_IMAGE_CODE = 1003
    }

    private lateinit var image: CircularImageView
    private lateinit var prefInfo: SharedPreferences
    private lateinit var edtUsername: TextInputLayout
    private lateinit var edtPass: TextInputLayout
    private lateinit var edtBio: TextInputLayout
    private lateinit var edtEmail: TextInputLayout
    lateinit var profileImage: MultipartBody.Part
    private lateinit var profile: Any

    private val viewModel = Profile_ViewModel()
    private var data: Uri? = null
    private var userId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        SetupViews()

    }

    private fun SetupViews() {
        prefInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        userId = prefInfo.getString("userId", "").toString()

        edtUsername = findViewById(R.id.til_editProfileActivity_username)
        edtPass = findViewById(R.id.til_editProfileActivity_password)
        edtBio = findViewById(R.id.til_editProfileActivity_bio)
        edtEmail = findViewById(R.id.til_editProfileActivity_email)
        image = findViewById(R.id.img_editProfileActivity_profile)
        val llProfile = findViewById<LinearLayout>(R.id.ll_editProfileActivity_changeProf)

        profile = intent.extras!!.getString("profile","")!!
        prefInfo = getSharedPreferences("UserInfo",Context.MODE_PRIVATE)
        val username = prefInfo.getString("username","")
        val password = prefInfo.getString("password","")
        val bio = intent.extras!!.getString("bio")
        val email = intent.extras!!.getString("email")

        LoadImageProfile("${ApiClient().baseUrl}${profile}",img_editProfileActivity_profile)

        edtUsername.editText?.setText(username)
        edtPass.editText?.setText(password)
        til_editProfileActivity_bio.editText?.setText(bio)
        til_editProfileActivity_email.editText?.setText(email)

        llProfile.setOnClickListener {
            SelectImage(SELECT_IMAGE_CODE)
        }
        btn_editProfileActivity_saveChange.setOnClickListener {
            if (data != null)
                UpdateProfileImage(data!!)
            else{
                if (edtUsername.editText?.text.toString().isNotEmpty() &&
                    edtPass.editText?.text.toString().isNotEmpty() &&
                    edtEmail.editText?.text.toString().isNotEmpty() &&
                    edtBio.editText?.text.toString().isNotEmpty())
                    UpdateMyProfile()
            }
        }

        img_editProfileActivity_back.setOnClickListener {
            finish()
        }
    }

    private fun UpdateMyProfile() {

        val oldUsername = prefInfo.getString("username", "")
        val oldPass = prefInfo.getString("password", "")

        viewModel.updateProfile(oldUsername!!,
            oldPass!!,
            edtUsername.editText?.text.toString(),
            edtPass.editText?.text.toString(),
            edtEmail.editText?.text.toString(),
            edtBio.editText?.text.toString(),
            object : Profile_ViewModel.OnUpdateProfile {
                override fun onUpdated() {
                    prefInfo.edit()
                        .putString("username", edtUsername.editText?.text.toString())
                        .putString("password", edtPass.editText?.text.toString())
                        .apply()
                    finish()
                }

                override fun onFailure() {
                    runOnUiThread(Runnable {
                        Toast.makeText(this@EditProfileActivity,
                            "خطا در ارتباط با سرور!",
                            Toast.LENGTH_SHORT).show()
                    })
                }
            })
    }

    private fun UpdateProfileImage(data: Uri) {
        val upload = UriToUploadable(this)
        profileImage = upload.getUploaderFile(data, "pImage", "${UUID.randomUUID()}")

        val rBUsername =
            RequestBody.create(okhttp3.MultipartBody.FORM, edtUsername.editText?.text.toString())
        val rBPass =
            RequestBody.create(okhttp3.MultipartBody.FORM, edtPass.editText?.text.toString())

        viewModel.updateProfileImage(rBUsername, rBPass,
            profileImage,
            object : Profile_ViewModel.OnUpdateProfileImage {
                override fun onUpdated() {
                    runOnUiThread(Runnable {
                        Toast.makeText(this@EditProfileActivity,
                            "عکس نمایه بروزرسانی شد.",
                            Toast.LENGTH_SHORT).show()
                        if (edtUsername.editText?.text.toString().isNotEmpty() &&
                            edtPass.editText?.text.toString().isNotEmpty() &&
                            edtEmail.editText?.text.toString().isNotEmpty() &&
                            edtBio.editText?.text.toString().isNotEmpty())
                            UpdateMyProfile()
                    })
                }

                override fun onFailure() {
                    runOnUiThread(Runnable {
                        Toast.makeText(this@EditProfileActivity,
                            "خطا در ست کردن عکس!",
                            Toast.LENGTH_SHORT).show()
                    })
                }
            })
    }

    private fun SelectImage(requestCode: Int) {
        val gallery = Intent(Intent.ACTION_GET_CONTENT)
        gallery.type = "image/*"
        startActivityForResult(gallery, requestCode)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_IMAGE_CODE && resultCode == Activity.RESULT_OK && data != null) {
            image.setImageURI(data.data)
            this.data = data.data
        }
    }
}