package com.github.sookhee.shinhanesgmarket.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.shinhanesgmarket.databinding.ItemPhotoSmallBinding
import com.github.sookhee.shinhanesgmarket.utils.setImageWithUrl

class PhotoAdapter :
    RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    var viewType: PhotoViewType = PhotoViewType.WRAP_CONTENT
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
        holder.bind(viewType, items[position])
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
            binding.onItemClick = onItemClick
        }

        fun bind(
            viewType: PhotoViewType,
            uri: String,
        ) {
            if (viewType == PhotoViewType.MATCH_PARENT) {
                binding.itemLayout.layoutParams.apply {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }
            }

            binding.photoUri = uri
            binding.productImage.setImageWithUrl(uri)
        }
    }

    companion object {
        enum class PhotoViewType { MATCH_PARENT, WRAP_CONTENT }
    }
}
