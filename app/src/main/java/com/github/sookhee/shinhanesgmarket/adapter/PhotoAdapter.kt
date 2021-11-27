package com.github.sookhee.shinhanesgmarket.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.shinhanesgmarket.databinding.ItemPhotoSmallBinding

class PhotoAdapter:
    RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    var items: List<String> = listOf()
    var onItemClick: ((selectedItem: String) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(list: List<String>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemPhotoSmallBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(
        private val binding: ItemPhotoSmallBinding,
        onItemClick: ((selectedItem: String) -> Unit)?,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
//            binding.onItemClick = onItemClick
        }

        fun bind(
            uri: String
        ) {
            binding.productImage.setImageURI(uri.toUri())
        }
    }
}
