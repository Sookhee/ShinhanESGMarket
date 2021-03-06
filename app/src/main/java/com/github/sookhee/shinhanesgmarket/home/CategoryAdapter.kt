package com.github.sookhee.shinhanesgmarket.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.domain.entity.Category
import com.github.sookhee.shinhanesgmarket.databinding.ItemCategoryBinding
import com.github.sookhee.shinhanesgmarket.utils.setImageWithUrl

class CategoryAdapter :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var items: List<Category> = emptyList()
    var onItemClick: ((selectedItem: Category) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(list: List<Category>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCategoryBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(
        private val binding: ItemCategoryBinding,
        onItemClick: ((selectedItem: Category) -> Unit)?,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(
            item: Category,
        ) {
            binding.item = item
            binding.categoryImage.setImageWithUrl(item.image)
        }
    }
}
