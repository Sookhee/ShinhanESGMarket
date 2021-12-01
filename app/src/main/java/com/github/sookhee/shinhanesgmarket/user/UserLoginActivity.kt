package com.github.sookhee.shinhanesgmarket.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.shinhanesgmarket.MainActivity
import com.github.sookhee.shinhanesgmarket.databinding.ActivityUserLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserLoginBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setOnClickListener()

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setOnClickListener() {
        binding.btnJoin.setOnClickListener {
            val intent = Intent(this, UserJoinActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val employeeNo = binding.inputEmployeeNo.text
            val password = binding.inputPassword.text.toString()

            // 로그인 로직
            val db = Firebase.auth
            db.signInWithEmailAndPassword("$employeeNo@doremi.com", password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        finish()
                    } else {
                        Log.w("민지", "signInWithEmailAndPassword: failure", it.exception)
                        Toast.makeText(this, "로그인 실패ㅠㅠ 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}