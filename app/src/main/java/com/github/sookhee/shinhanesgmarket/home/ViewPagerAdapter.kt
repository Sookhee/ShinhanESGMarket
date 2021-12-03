package com.github.sookhee.shinhanesgmarket.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.domain.entity.Banner
import com.github.sookhee.shinhanesgmarket.databinding.ItemBannerBinding
import com.github.sookhee.shinhanesgmarket.utils.setImageWithUrl
import java.lang.Exception

class ViewPagerAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var items = emptyList<Banner>()
    var onItemClick: ((selectedItem: Banner) -> Unit)? = null
    val firstElementPosition = Int.MAX_VALUE / 2

    fun setItem(list: List<Banner>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClick)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position.rem(items.size)])
    }

    override fun getItemCount(): Int = Int.MAX_VALUE
}

class ViewHolder(
    private val binding: ItemBannerBinding,
    private val onItemClick: ((selectedItem: Banner) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("Range")
    fun bind(item: Banner) {
        try {
            binding.item = item
            binding.onItemClick = onItemClick
            binding.bannerImage.setImageWithUrl(item.image)
        } catch (e: Exception) {

        }
    }
}
