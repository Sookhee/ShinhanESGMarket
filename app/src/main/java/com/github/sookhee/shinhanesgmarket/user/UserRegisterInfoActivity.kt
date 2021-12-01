package com.github.sookhee.shinhanesgmarket.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.domain.entity.User
import com.github.sookhee.shinhanesgmarket.databinding.ActivityUserRegisterInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRegisterInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserRegisterInfoBinding
    private lateinit var viewModel: UserViewModel

    private var toast: Toast? = null
    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserRegisterInfoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        initView()
        setOnClickListener()
        setObserver()

        setContentView(binding.root)
    }

    private fun setObserver() {
        viewModel.registerUserState.observe(this) { state ->
            when(state) {
                UserState.LOADING -> {
                    toast = Toast.makeText(this, "회원가입 중...", Toast.LENGTH_SHORT)
                    toast?.show()
                }
                UserState.FAIL -> {
                    toast?.cancel()

                    toast = Toast.makeText(this, "회원가입 실패ㅠㅠ\n다시 시도해주세요", Toast.LENGTH_SHORT)
                    toast?.show()
                }
                UserState.SUCCESS -> {
                    toast?.cancel()

                    toast = Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT)
                    toast?.show()

                    finish()
                }
            }
        }
    }

    private fun initView() {
        val intent = intent

        user.user_id = intent.getStringExtra("userId") ?: ""
        user.employee_no = intent.getStringExtra("employeeNo") ?: ""
        user.name = intent.getStringExtra("name") ?: ""
        user.branch_nm = intent.getStringExtra("branchNm") ?: ""

        binding.inputName.setText(user.name)
        binding.inputBranchNm.setText(user.branch_nm)
    }

    private fun setOnClickListener() {
        binding.btnJoin.setOnClickListener {
            joinUser()
        }
    }

    private fun joinUser() {
        user.name = binding.inputName.text.toString()
        user.nickname = binding.inputNickName.text.toString()
        user.user_pw = binding.inputPassword.text.toString()
        user.branch_nm= binding.inputBranchNm.text.toString()

        viewModel.registerUser(user)
    }
}