package com.github.sookhee.shinhanesgmarket.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.shinhanesgmarket.databinding.ActivityProductBinding
import com.github.sookhee.shinhanesgmarket.utils.withComma
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        val intent = getIntent()
        val productId = intent.getStringExtra("PRODUCT_ID")

        productId?.let { viewModel.getProductDetail(productId) }
        observeFlow()

        setContentView(binding.root)
    }

    private fun observeFlow() {
        viewModel.product.observe(this) {
            binding.sellerName.text = it.owner
            binding.sellerArea.text = it.area
            binding.productTitle.text = it.title
            binding.productPrice.text = "${it.price.withComma()}원"
            binding.productTime.text = it.updatedAt
            binding.productDescription.text = it.content
        }
    }
}
