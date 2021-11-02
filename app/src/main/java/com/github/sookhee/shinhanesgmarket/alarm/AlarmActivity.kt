package com.github.sookhee.shinhanesgmarket.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sookhee.shinhanesgmarket.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)

        setOnClickListener()

        setContentView(binding.root)
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}