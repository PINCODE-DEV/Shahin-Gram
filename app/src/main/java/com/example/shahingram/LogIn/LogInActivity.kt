package com.example.shahingram.LogIn

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.shahingram.MainActivity
import com.example.shahingram.R
import com.example.shahingram.SetStatusBarColor
import com.example.shahingram.SignUp.SignUpActivity
import kotlinx.android.synthetic.main.activity_log_in.*


class LogInActivity : AppCompatActivity() {
    private lateinit var prefInfo: SharedPreferences
    private var hidePass: Boolean = true
    private lateinit var edtUsername: EditText
    private lateinit var edtPass: EditText
    private val viewModel = LogIn_ViewModel.STon.instance

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var activity: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        activity = this

        SetStatusBarColor(this)
        SetupViews()
    }

    private fun SetupViews() {
        Log.i("LOG", "onCreateView: test check")
        prefInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        if (prefInfo.getString("username","")!!.isNotEmpty()) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        edtUsername = findViewById<EditText>(R.id.edt_logInActivity_username)
        edtPass = findViewById<EditText>(R.id.edt_logInActivity_password)
        val imgShowPass = findViewById<ImageView>(R.id.img_logInActivity_showPass)
        val btnLogIn = findViewById<Button>(R.id.btn_logInActivity_logIn)

        edtUsername.addTextChangedListener { text ->
            if (text.toString().isNotEmpty() && edtPass.text.toString()
                    .isNotEmpty()
            ) {
                btnLogIn.setBackgroundColor(getColor(R.color.colorPrimary))
                btnLogIn.isEnabled = true
            } else {
                btnLogIn.setBackgroundColor(getColor(R.color.blue_disable))
                btnLogIn.isEnabled = false
            }
        }

        edtPass.addTextChangedListener { text ->
            if (text.toString().isNotEmpty() && edtUsername.text.toString()
                    .isNotEmpty()
            ) {
                btnLogIn.setBackgroundColor(getColor(R.color.colorPrimary))
                btnLogIn.isEnabled = true
            } else {
                btnLogIn.setBackgroundColor(getColor(R.color.blue_disable))
                btnLogIn.isEnabled = false
            }
        }

        imgShowPass.setOnClickListener {
            if (hidePass) {
                hidePass = false
                imgShowPass.setImageResource(R.drawable.ic_show_pass)
                edtPass.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                edtPass.setSelection(edtPass.getText().length)
            } else {
                hidePass = true
                imgShowPass.setImageResource(R.drawable.ic_hide_pass)
                edtPass.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                edtPass.setSelection(edtPass.getText().length)
            }
        }

        btnLogIn.setOnClickListener {
            Login()
        }

        txt_logInActivity_signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun Login() {
        viewModel.logIn(
            edtUsername.text.toString(),
            edtPass.text.toString(),
            object : LogIn_ViewModel.OnLogInResponse {
                override fun onResponse(model : LogIn) {
                    if (model.status.equals("success")) {
                        prefInfo.edit()
                            .putString("userId", model.userId)
                            .putString("username", edtUsername.text.toString())
                            .putString("password", edtPass.text.toString())
                            .apply()

                        val intent = Intent(this@LogInActivity, MainActivity::class.java)
                        runOnUiThread(Runnable {
                            startActivity(intent)
                        })
                        finish()
                    } else
                        runOnUiThread(Runnable {
                            Toast.makeText(
                                this@LogInActivity,
                                "کاربری با مشخصات وارد شده وجود ندارد!",
                                Toast.LENGTH_LONG
                            ).show()
                        })
                }

                override fun onFailure() {
                    runOnUiThread(Runnable {
                        Toast.makeText(
                            this@LogInActivity,
                            "خطا در ارتباط با سرور!",
                            Toast.LENGTH_LONG
                        ).show()
                    })
                }
            })
    }
}