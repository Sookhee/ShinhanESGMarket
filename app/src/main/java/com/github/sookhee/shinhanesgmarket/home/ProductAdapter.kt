package com.github.sookhee.shinhanesgmarket.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.shinhanesgmarket.databinding.ItemProductBinding
import com.github.sookhee.shinhanesgmarket.utils.calcTime
import com.github.sookhee.shinhanesgmarket.utils.withComma

class ProductAdapter :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var items: List<Product> = emptyList()
    var onItemClick: ((selectedItem: Product) -> Unit)? = null

    fun setItem(list: List<Product>) {
        items = list
        notifyDataSetChanged()
    }

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
            binding.item = item
            binding.productTime.text = item.updatedAt.calcTime()
            binding.productPrice.text = if (item.price == 0) "무료나눔" else "${item.price.withComma()}원"

            Glide.with(binding.root)
                .load(item.photoList.get(0))
                .into(binding.productImage)
        }
    }
}
