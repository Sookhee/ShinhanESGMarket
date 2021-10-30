package com.github.sookhee.shinhanesgmarket.chatting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.domain.entity.ChatPreview
import com.github.sookhee.shinhanesgmarket.databinding.ItemChatPreviewBinding

class ChatPreviewAdapter :
    RecyclerView.Adapter<ChatPreviewAdapter.ViewHolder>() {

    var items: List<ChatPreview> = emptyList()
    var onItemClick: ((selectedItem: ChatPreview) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemChatPreviewBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(
        private val binding: ItemChatPreviewBinding,
        onItemClick: ((selectedItem: ChatPreview) -> Unit)?,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(
            item: ChatPreview,
        ) {
            binding.item = item
        }
    }
}
