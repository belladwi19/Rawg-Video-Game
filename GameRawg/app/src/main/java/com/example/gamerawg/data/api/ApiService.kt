package com.example.gamerawg.data.api

import com.example.gamerawg.BuildConfig
import com.example.gamerawg.data.response.GameResponseResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET
    fun getDataNext(
        @Url urlString: String
    ): Single<GameResponseResult>

    @GET("games")
    fun getData(
        @Query("key") key: String = BuildConfig.RAWG_KEY,
        @Query("page_size") pageSize: Int = 10,
        @Query("platforms") platformID: Int = 1,
        @Query("page") page: Int = 1
    ): Single<GameResponseResult>

    @GET("games")
    fun getDataBySearch(
        @Query("key") key: String = BuildConfig.RAWG_KEY,
        @Query("page_size") pageSize: Int = 10,
        @Query("platforms") platformID: Int = 4,
        @Query("search") search: String,
        @Query("page") page: Int = 1
    ): Single<GameResponseResult>
}