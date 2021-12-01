package com.github.sookhee.shinhanesgmarket.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.shinhanesgmarket.databinding.ActivityUserJoinBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserJoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserJoinBinding
    private lateinit var viewModel: UserViewModel

    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserJoinBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setOnClickListener()
        setObserver()

        setContentView(binding.root)
    }

    private fun setOnClickListener() {
        binding.btnNextstep.setOnClickListener {
            val employeeNo = binding.inputEmployeeNo.text.toString()

            if (employeeNo.length == 8) {
                viewModel.getUserInfo(employeeNo)
            } else {
                Toast.makeText(this, "행번 양식이 맞지 않습니다. 다시 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setObserver() {
        viewModel.searchUseState.observe(this) { status ->
            when (status) {
                UserState.LOADING -> {
                    toast = Toast.makeText(this, "직원 조회 중...", Toast.LENGTH_SHORT)
                    toast?.show()
                }
                UserState.SUCCESS -> {
                    toast?.cancel()
                }
                UserState.FAIL -> {
                    toast?.cancel()

                    toast = Toast.makeText(this, "직원 조회 실패. 다시 시도해주세요", Toast.LENGTH_SHORT)
                    toast?.show()
                }
            }
        }

        viewModel.userInfo.observe(this) {
            if (it.nickname == "") {
                val intent = Intent(this, UserRegisterInfoActivity::class.java)

                intent.putExtra("employeeNo", viewModel.userInfo.value?.employee_no)
                intent.putExtra("name", viewModel.userInfo.value?.name)
                intent.putExtra("userId", viewModel.userInfo.value?.user_id)
                intent.putExtra("branchNm", viewModel.userInfo.value?.branch_nm)
                intent.putExtra("branchNo", viewModel.userInfo.value?.branch_no)

                startActivity(intent)
            } else {
                toast?.cancel()

                toast = Toast.makeText(this, "이미 가입된 회원입니다", Toast.LENGTH_SHORT)
                toast?.show()
            }

            finish()
        }
    }
}