package com.github.sookhee.shinhanesgmarket.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sookhee.shinhanesgmarket.databinding.ActivityUserRegisterInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRegisterInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserRegisterInfoBinding
    private var employeeId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserRegisterInfoBinding.inflate(layoutInflater)

        initView()

        setContentView(binding.root)
    }

    private fun initView() {
        val intent = intent

        employeeId = intent.getStringExtra("employeeNo") ?: ""
        val name = intent.getStringExtra("name") ?: ""
        val branchNm = intent.getStringExtra("branchNm") ?: ""

        binding.inputName.setText(name)
        binding.inputBranchNm.setText(branchNm)
    }
}