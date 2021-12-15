package com.github.sookhee.shinhanesgmarket.user

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.domain.entity.User
import com.github.sookhee.shinhanesgmarket.AppApplication
import com.github.sookhee.shinhanesgmarket.MainActivity
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.databinding.ActivityUserLoginBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserLoginBinding
    private lateinit var viewModel: UserViewModel

    private lateinit var loginIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setOnClickListener()
        setObserver()
        initView()

        setContentView(binding.root)
    }

    private fun setOnClickListener() {
        binding.btnLogin.setOnClickListener {
            val employeeNo = binding.inputEmployeeNo.text.toString()
            val password = binding.inputPassword.text.toString()

            if (employeeNo.isNotBlank() && password.isNotBlank()) {
                checkAuth(employeeNo, password)
            } else {
                Toast.makeText(this, "로그인 정보를 정확히 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAuth(employeeNo: String, password: String) {
        // 로그인 로직
        val db = Firebase.auth
        db.signInWithEmailAndPassword("$employeeNo@doremi.com", password)
            .addOnFailureListener {
                when (it) {
                    // 게정없음
                    is FirebaseAuthInvalidUserException -> {
                        if (password == "shinhan1!") {
                            viewModel.registerUser(User(
                                employee_no = employeeNo,
                                user_pw = password
                            ))
                        } else {
                            loginFail()
                        }
                    }

                    // 네크워크 에러
                    is FirebaseNetworkException -> {
                        // showDialog()
                    }

                    else -> {
                        loginFail()
                    }
                }
            }
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    loginSuccess(employeeNo)
                }
            }
    }

    private fun loginFail() {
        Toast.makeText(this, "로그인 실패ㅠㅠ 다시 시도해주세요", Toast.LENGTH_SHORT).show()
    }

    private fun loginSuccess(employeeNo: String) {
        Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()

        loginIntent = Intent(this, MainActivity::class.java)

        viewModel.getUserInfo(employeeNo)
    }

    private fun setObserver() {
        viewModel.userInfo.observe(this) {
            val app = AppApplication.getInstance()
            app.setLoginInfo(it)

            startActivity(loginIntent)

            finish()
        }

        viewModel.registerUserState.observe(this) {
            when(it){
                UserState.SUCCESS -> {
                    loginSuccess(binding.inputEmployeeNo.text.toString())
                }

                UserState.FAIL -> {
                    loginFail()

                    val dialog = Dialog(this).apply {
                        requestWindowFeature(Window.FEATURE_NO_TITLE)
                        setContentView(R.layout.layout_custom_dialog)
                    }

                    showErrorDialog(dialog)
                }
            }
        }
    }

    private fun showErrorDialog(dialog: Dialog) {
        dialog.show()

        dialog.findViewById<ImageView>(R.id.dialogImage).apply {
            setImageResource(R.drawable.dialog_exception)
            (layoutParams as LinearLayout.LayoutParams).setMargins(200, 0, 200, 0)
        }

        dialog.findViewById<TextView>(R.id.dialogText).text = getString(R.string.dialog_exception)

        dialog.findViewById<TextView>(R.id.dialogButton).apply {
            text = getString(R.string.dialog_close)
            setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    private fun initView() {
        binding.inputEmployeeNo.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) return@setOnKeyListener true
            return@setOnKeyListener false
        }
    }

    companion object {
        private val TAG = UserLoginActivity::class.simpleName
    }
}