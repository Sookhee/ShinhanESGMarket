package com.github.sookhee.shinhanesgmarket.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.shinhanesgmarket.R

class ViewPagerAdapter : RecyclerView.Adapter<ViewHolder>() {
    var items = mutableListOf<String>()
    var onItemClick: ((selectedItem: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textView: TextView = itemView.findViewById(R.id.itemTextView)

    fun bind(text: String, position: Int) {
        textView.text = text
    }
}
