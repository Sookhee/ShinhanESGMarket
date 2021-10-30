package com.github.sookhee.shinhanesgmarket.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sookhee.shinhanesgmarket.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}