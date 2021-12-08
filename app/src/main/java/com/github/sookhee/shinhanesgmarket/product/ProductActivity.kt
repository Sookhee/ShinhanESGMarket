package com.github.sookhee.shinhanesgmarket.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.shinhanesgmarket.AppApplication
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.adapter.PhotoAdapter
import com.github.sookhee.shinhanesgmarket.chatting.ChatRoomActivity
import com.github.sookhee.shinhanesgmarket.databinding.ActivityProductBinding
import com.github.sookhee.shinhanesgmarket.utils.calcTime
import com.github.sookhee.shinhanesgmarket.utils.parseCategory
import com.github.sookhee.shinhanesgmarket.utils.withComma
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private lateinit var viewModel: ProductViewModel

    private val app = AppApplication.getInstance()
    private val loginInfo = app.getLoginInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        val intent = getIntent()
        val productId = intent.getStringExtra("PRODUCT_ID")

        productId?.let {
            viewModel.getProductDetail(productId)
            viewModel.isLikeProduct(loginInfo.employee_no, it)
        }

        observeFlow()
        setOnClickListener()

        setContentView(binding.root)
    }

    private fun observeFlow() {
        viewModel.product.observe(this) {
            binding.sellerName.text = it.owner
            binding.sellerArea.text = it.area
            binding.productTitle.text = it.title
            binding.productPrice.text = if (it.price == 0) "무료나눔" else "${it.price.withComma()}원"
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

        viewModel.isLikeProduct.observe(this) {
            if (it.isEmpty()) {
                binding.productHeart.setImageResource(R.drawable.ic_heart_off)
            } else {
                binding.productHeart.setImageResource(R.drawable.ic_heart_on)
            }
        }
    }

    private fun setOnClickListener() {
        binding.productHeart.setOnClickListener {
            viewModel.toggleProductHeart(loginInfo.employee_no)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnChat.setOnClickListener {
            val intent = Intent(this, ChatRoomActivity::class.java)

            intent.putExtra("product_id", viewModel.product.value?.id)
            intent.putExtra("product_title", viewModel.product.value?.title)
            intent.putExtra("product_price", viewModel.product.value?.price)
            intent.putExtra("product_owner", viewModel.product.value?.owner)
            intent.putExtra("product_owner_id", viewModel.product.value?.owner_id)
            intent.putExtra("product_photo", viewModel.product.value!!.photoList["0"])
            intent.putExtra("product_area", viewModel.product.value!!.area)

            startActivity(intent)
        }
    }
}
