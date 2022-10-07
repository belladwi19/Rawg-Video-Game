package com.example.gamerawg.data.util

import androidx.recyclerview.widget.DiffUtil
import com.example.gamerawg.data.model.GameList

class Callback : DiffUtil.ItemCallback<GameList>() {

    override fun areItemsTheSame(oldItem: GameList, newItem: GameList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameList, newItem: GameList): Boolean {
        return oldItem == newItem
    }
}