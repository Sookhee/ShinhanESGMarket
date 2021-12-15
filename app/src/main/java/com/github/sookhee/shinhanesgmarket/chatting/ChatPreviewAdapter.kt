package com.github.sookhee.shinhanesgmarket.chatting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.domain.entity.ChatPreview
import com.github.sookhee.shinhanesgmarket.databinding.ItemChatPreviewBinding
import com.github.sookhee.shinhanesgmarket.utils.calcTime
import com.github.sookhee.shinhanesgmarket.utils.setImageWithUrl
import java.text.SimpleDateFormat
import java.util.*

class ChatPreviewAdapter :
    RecyclerView.Adapter<ChatPreviewAdapter.ViewHolder>() {

    var employeeId: String = ""
    var items: List<ChatPreview> = emptyList()
    var onItemClick: ((selectedItem: ChatPreview) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(list: List<ChatPreview>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemChatPreviewBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], employeeId)
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
            employeeId: String
        ) {
            binding.item = item
            binding.traderProduct.setImageWithUrl(item.product_image)

            val simpleDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
            val lastTime = simpleDate.format(Date(item.last_time.toLong()))
            binding.lastChatTime.text = lastTime.calcTime()
            binding.traderName.text = if (employeeId == item.seller_id) item.buyer_name else item.seller_name
            binding.traderImage.clipToOutline = true
        }
    }
}
