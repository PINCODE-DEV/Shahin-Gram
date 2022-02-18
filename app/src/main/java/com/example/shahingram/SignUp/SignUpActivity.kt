package com.example.shahingram.SignUp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.shahingram.LogIn.LogIn
import com.example.shahingram.LogIn.LogInActivity
import com.example.shahingram.MainActivity
import com.example.shahingram.R
import com.example.shahingram.SetStatusBarColor

class SignUpActivity : AppCompatActivity() {
    private var hidePass: Boolean = true
    private val viewModel = SignUp_ViewModel.STon.instance
    private lateinit var prefInfo : SharedPreferences
    private lateinit var edtUsername: EditText
    private lateinit var edtPass: EditText
    private lateinit var edtEmail: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        SetStatusBarColor(this)

        SetupViews()

    }

    private fun SetupViews() {

        prefInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

        edtUsername = findViewById(R.id.edt_signUpActivity_username)
        edtPass = findViewById(R.id.edt_signUpActivity_password)
        edtEmail = findViewById(R.id.edt_signUpActivity_email)
        val imgShowPass = findViewById<ImageView>(R.id.img_signUpActivity_showPass)
        val btnSignUp = findViewById<Button>(R.id.btn_signUpActivity_signUp)

        edtUsername.addTextChangedListener { text ->
            if (text.toString().isNotEmpty() && edtPass.text.toString()
                    .isNotEmpty()
            ) {
                btnSignUp.setBackgroundColor(getColor(R.color.colorPrimary))
                btnSignUp.isEnabled = true
            } else {
                btnSignUp.setBackgroundColor(getColor(R.color.blue_disable))
                btnSignUp.isEnabled = false
            }
        }

        edtPass.addTextChangedListener { text ->
            if (text.toString().isNotEmpty() && edtUsername.text.toString()
                    .isNotEmpty()
            ) {
                btnSignUp.setBackgroundColor(getColor(R.color.colorPrimary))
                btnSignUp.isEnabled = true
            } else {
                btnSignUp.setBackgroundColor(getColor(R.color.blue_disable))
                btnSignUp.isEnabled = false
            }
            if (edtPass.text.toString().trim().length < 6)
                edtPass.error = "Password is weak"
            else
                edtPass.error = null
        }

        imgShowPass.setOnClickListener {
            if (hidePass) {
                hidePass = false
                imgShowPass.setImageResource(R.drawable.ic_show_pass)
                edtPass.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                edtPass.setSelection(edtPass.text.length)
            } else {
                hidePass = true
                imgShowPass.setImageResource(R.drawable.ic_hide_pass)
                edtPass.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                edtPass.setSelection(edtPass.text.length)
            }
        }

        btnSignUp.setOnClickListener {
            SignUp()
        }

    }

    private fun SignUp() {
        viewModel.signUp(
            edtUsername.text.toString(),
            edtPass.text.toString(),
            edtEmail.text.toString(),
            object : SignUp_ViewModel.OnSignUpResponse {
                override fun onResponse(model : LogIn) {
                    if (model.status.equals("success")) {
                        prefInfo.edit()
                            .putString("userId", model.userId)
                            .putString("username", edtUsername.text.toString())
                            .putString("password", edtPass.text.toString())
                            .apply()
                        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                        runOnUiThread(Runnable {
                            startActivity(intent)
                        })
                        finish()
                        LogInActivity.activity.finish()
                    } else
                        runOnUiThread(Runnable {
                            Toast.makeText(
                                this@SignUpActivity,
                                "نام کاربری یا ایمیل تکراری است!",
                                Toast.LENGTH_LONG
                            ).show()
                        })
                }

                override fun onFailure() {
                    runOnUiThread(Runnable {
                        Toast.makeText(
                            this@SignUpActivity,
                            "خطا در ارتباط با سرور!",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    })
                }
            })
    }
}