package com.github.sookhee.shinhanesgmarket.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.domain.entity.Banner
import com.github.sookhee.shinhanesgmarket.databinding.ItemBannerBinding
import com.github.sookhee.shinhanesgmarket.utils.fromHtml
import com.github.sookhee.shinhanesgmarket.utils.setImageWithUrl
import java.lang.Exception

class ViewPagerAdapter : RecyclerView.Adapter<ViewHolder>() {
    var items = mutableListOf<Banner>()
    var onItemClick: ((selectedItem: Banner) -> Unit)? = null
    val firstElementPosition = Int.MAX_VALUE / 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position.rem(items.size)])
    }

    override fun getItemCount(): Int = Int.MAX_VALUE
}

class ViewHolder(private val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("Range")
    fun bind(item: Banner) {
        try {
            binding.item = item
            binding.message.text = item.message.fromHtml()
            binding.emoji.text = item.emoji.fromHtml()
            binding.bannerBackground.setBackgroundColor(Color.parseColor(item.backgroundColor))
            binding.bottomBackground.setBackgroundColor(Color.parseColor(item.backgroundColor))
            binding.imageView.setImageWithUrl(item.imageUrl)
        } catch (e: Exception) {

        }
    }
}
