package com.xiaolf.rv.click.sample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ListAdapter(
    private val context: Context
) : Adapter<ListAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
        val headerImageView: ImageView = itemView.findViewById(R.id.iv_header)
        val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        val msgTextView: TextView = itemView.findViewById(R.id.tv_msg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_simple, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.headerImageView.setImageResource(R.drawable.ic_launcher_background)
        holder.titleTextView.text = "Title $position"
        holder.msgTextView.text = "Message message message $position"
    }

    override fun getItemCount(): Int {
        return 100
    }
}