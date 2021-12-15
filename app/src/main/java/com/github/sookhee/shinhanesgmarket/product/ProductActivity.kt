package com.github.sookhee.shinhanesgmarket.product

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.shinhanesgmarket.AppApplication
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.adapter.PhotoAdapter
import com.github.sookhee.shinhanesgmarket.chatting.ChatRoomActivity
import com.github.sookhee.shinhanesgmarket.databinding.ActivityProductBinding
import com.github.sookhee.shinhanesgmarket.utils.calcTime
import com.github.sookhee.shinhanesgmarket.utils.withComma
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

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
        viewModel.product.observe(this) { product ->
            binding.sellerName.text = product.owner_name
            binding.sellerArea.text = product.area_name
            binding.productTitle.text = product.title
            binding.productPrice.text = if (product.price == 0) "무료나눔" else "${product.price.withComma()}원"
            binding.productTime.text = product.updated_at.calcTime()
            binding.productDescription.text = product.content
            binding.sellerImage.clipToOutline = true

            try {
                binding.productCategory.text = AppApplication.getInstance().getCategoryList().filter { it.id == product.category_id }[0].name
            } catch (e: Exception) {
                binding.productCategory.text = ""
            }

            binding.photoRecyclerView.apply {
                adapter = PhotoAdapter().apply {
                    viewType = PhotoAdapter.Companion.PhotoViewType.MATCH_PARENT
                    items = product.photoList.values.toList()
                    onItemClick = {
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

        viewModel.stateError.observe(this) {
            if (it) {
                val dialog = Dialog(this).apply {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    setContentView(R.layout.layout_custom_dialog)
                }

                showErrorDialog(dialog)
            }
        }
    }

    private fun showErrorDialog(dialog: Dialog) {
        dialog.show()

        dialog.findViewById<ImageView>(R.id.dialogImage).apply {
            setImageResource(R.drawable.dialog_exception)
            (layoutParams as LinearLayout.LayoutParams).setMargins(200, 0, 200, 0)
        }

        dialog.findViewById<TextView>(R.id.dialogText).text = getString(R.string.dialog_exception)

        dialog.findViewById<TextView>(R.id.dialogButton).apply {
            text = getString(R.string.dialog_close)
            setOnClickListener {
                dialog.dismiss()
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
            intent.putExtra("product_owner", viewModel.product.value?.owner_name)
            intent.putExtra("product_owner_id", viewModel.product.value?.owner_id)
            intent.putExtra("product_image", viewModel.product.value!!.photoList["0"])
            intent.putExtra("product_area", viewModel.product.value!!.area_name)

            startActivity(intent)
        }
    }
}
