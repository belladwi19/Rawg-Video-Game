package com.example.gamerawg.data.repository

import com.example.gamerawg.data.response.GameResponseResult
import io.reactivex.Single

interface DataRepository {

    fun getData(): Single<GameResponseResult>
    fun getNextUrl(url: String): Single<GameResponseResult>
    fun getDatasBySearch(search: String): Single<GameResponseResult>

}