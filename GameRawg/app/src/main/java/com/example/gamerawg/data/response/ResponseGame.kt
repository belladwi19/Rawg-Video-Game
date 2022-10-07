package com.example.gamerawg.data.response

import com.example.gamerawg.data.model.GameList

fun GameResponseResult.gameListing(): Pair<String?, ArrayList<GameList>> {
    val list = ArrayList(
        this.results.map {
            GameList(
                it.id,
                it.name,
                it.background_image,
                it.rating_top
            )
        }
    )
    return Pair(this.next, list)
}

fun GameResponseResult.gamePlatform(): ArrayList<GameList> {
    return ArrayList(
        this.results.map {
            GameList(
                it.id,
                it.name,
                it.background_image,
                it.rating_top
            )
        })
}