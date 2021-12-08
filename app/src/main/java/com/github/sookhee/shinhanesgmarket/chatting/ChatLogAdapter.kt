package com.github.sookhee.shinhanesgmarket.chatting

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.domain.entity.ChatLog
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.databinding.ItemChatLogBinding

class ChatLogAdapter :
    RecyclerView.Adapter<ChatLogAdapter.ViewHolder>() {

    var items: MutableList<ChatLog> = mutableListOf()
    var employeeId: String = ""

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(list: List<ChatLog>) {
        items = list.toMutableList()
        notifyDataSetChanged()
    }

    fun addItem(log: ChatLog) {
        items.add(log)
        notifyItemInserted(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemChatLogBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], employeeId)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(
        private val binding: ItemChatLogBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor", "RtlHardcoded")
        fun bind(
            item: ChatLog,
            employeeId: String
        ) {
            binding.item = item
            if (item.sender_id != employeeId) {
                binding.chatContainer.gravity = Gravity.LEFT
                binding.textView.setTextColor(R.color.blue)
                binding.textView.setBackgroundResource(R.drawable.bubble_white)
            }
        }
    }
}
