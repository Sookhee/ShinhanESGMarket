package com.github.sookhee.shinhanesgmarket.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.shinhanesgmarket.databinding.ActivityCategoryBinding
import com.github.sookhee.shinhanesgmarket.home.ProductAdapter
import com.github.sookhee.shinhanesgmarket.product.ProductActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        setContentView(binding.root)

        viewModel.getProductListWithCategory(intent.getStringExtra(CATEGORY_ID) ?: "")

        initView()
        initProductRecyclerView()
        setOnClickListener()
        setObserver()
    }

    private fun initView() {
        binding.categoryName.text = intent.getStringExtra(CATEGORY_NAME)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getProductListWithCategory(intent.getStringExtra(CATEGORY_ID) ?: "")
        }
    }

    private fun initProductRecyclerView() {
        binding.categoryRecyclerView.apply {
            adapter = ProductAdapter().apply {
                onItemClick = {
                    val intent = Intent(context, ProductActivity::class.java)
                    intent.putExtra("PRODUCT_ID", it.id)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener { onBackPressed() }
    }

    private fun setObserver() {
        viewModel.productList.observe(this) {
            (binding.categoryRecyclerView.adapter as ProductAdapter).setItem(it)
        }
    }

    companion object {
        private const val CATEGORY_ID = "category_id"
        private const val CATEGORY_NAME = "category_name"
    }
}
