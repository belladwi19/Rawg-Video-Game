package com.example.gamerawg.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gamerawg.R
import com.example.gamerawg.data.model.GameList

class PlatformAdapter(var list: ArrayList<GameList>) :
    RecyclerView.Adapter<PlatformViewHolder>() {
    var prevPosition = -1
    var itemPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatformViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_filter, parent, false)
        return PlatformViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlatformViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun itemList(position: Int) {
        prevPosition = itemPosition
        itemPosition = position
        notifyItemChanged(itemPosition)
        notifyItemChanged(prevPosition)
    }

    fun updateList(newList: ArrayList<GameList>) {
        list = newList
        notifyDataSetChanged()
    }
}

class PlatformViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var filterName: TextView

    init {
        filterName = itemView.findViewById(R.id.tv_card)
    }

    fun bindData(platform: GameList) {
        filterName.text = platform.name
    }
}