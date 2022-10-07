package com.example.gamerawg.data.response

data class GameResponseAPI(
    val id: Int,
    val name: String,
    val background_image: String,
    val rating_top: Int
)

data class GameResponseResult(
    val results: List<GameResponseAPI>,
    val next: String?
)