package com.github.sookhee.shinhanesgmarket.mypage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.shinhanesgmarket.databinding.ItemProductGridBinding
import com.github.sookhee.shinhanesgmarket.utils.setImageWithUrl

class GridProductAdapter :
    RecyclerView.Adapter<GridProductAdapter.ViewHolder>() {

    var items: List<Product> = emptyList()
    var onItemClick: ((selectedItem: Product) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(list: List<Product>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemProductGridBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(
        private val binding: ItemProductGridBinding,
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

            binding.productImage.setImageWithUrl("https://firebasestorage.googleapis.com/v0/b/doremi-market.appspot.com/o/banner%2Fbanner_character_2.png?alt=media&token=829f89e5-0974-46c5-b247-e65655dcf8a7")
        }
    }
}
