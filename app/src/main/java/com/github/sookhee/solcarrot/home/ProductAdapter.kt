package com.github.sookhee.solcarrot.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.solcarrot.databinding.ItemProductBinding

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
        private val onItemClick: ((selectedItem: Product) -> Unit)?,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(
            item: Product,
        ) {
            binding.item = item
            binding.onItemClick = onItemClick

            binding.productPrice.text = "${item.price}"
        }
    }
}
