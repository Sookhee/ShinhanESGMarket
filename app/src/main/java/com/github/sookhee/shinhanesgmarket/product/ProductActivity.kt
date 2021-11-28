package com.github.sookhee.shinhanesgmarket.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.shinhanesgmarket.adapter.PhotoAdapter
import com.github.sookhee.shinhanesgmarket.databinding.ActivityProductBinding
import com.github.sookhee.shinhanesgmarket.utils.calcTime
import com.github.sookhee.shinhanesgmarket.utils.parseCategory
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
            binding.productPrice.text = if(it.price == 0) "무료나눔" else "${it.price.withComma()}원"
            binding.productCategory.text = it.category.parseCategory()
            binding.productTime.text = it.updatedAt.calcTime()
            binding.productDescription.text = it.content

            binding.photoRecyclerView.apply {
                adapter = PhotoAdapter().apply {
                    viewType = PhotoAdapter.Companion.PhotoViewType.MATCH_PARENT
                    items = it.photoList.values.toList()
                    onItemClick = {
                        Toast.makeText(context, "click: $it", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
