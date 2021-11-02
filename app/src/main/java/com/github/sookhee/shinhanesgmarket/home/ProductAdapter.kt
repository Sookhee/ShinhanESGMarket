package com.github.sookhee.shinhanesgmarket.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.shinhanesgmarket.databinding.ItemProductBinding
import java.text.DecimalFormat

class ProductAdapter :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var items: List<Product> = emptyList()
    var onItemClick: ((selectedItem: Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemProductBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(
        private val binding: ItemProductBinding,
        onItemClick: ((selectedItem: Product) -> Unit)?,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        @SuppressLint("SetTextI18n")
        fun bind(
            item: Product,
        ) {
            val dec = DecimalFormat("#,###")

            binding.item = item
            binding.productPrice.text = "${dec.format(item.price)}원"
        }
    }
}
