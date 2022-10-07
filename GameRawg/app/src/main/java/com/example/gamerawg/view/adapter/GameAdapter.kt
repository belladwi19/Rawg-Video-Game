package com.example.gamerawg.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamerawg.R
import com.example.gamerawg.data.model.GameList
import com.example.gamerawg.data.util.Callback

class GameAdapter : ListAdapter<GameList, GameAdapter.GameViewHolder>(Callback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.GameViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameAdapter.GameViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var name: TextView
        var rating: TextView

        init {
            image = itemView.findViewById(R.id.img_game)
            name = itemView.findViewById(R.id.tv_game)
            rating = itemView.findViewById(R.id.tv_rating_top)
        }

        fun bindData(game: GameList) {
            name.text = game.name
            Glide.with(itemView.context).load(game.backgroundImage).into(image)
            rating.text = "Rating: " + game.rating_top.toString()
        }
    }
}