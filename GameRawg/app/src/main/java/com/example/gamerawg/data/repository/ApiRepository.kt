package com.example.gamerawg.data.repository

import com.example.gamerawg.data.api.ApiService
import com.example.gamerawg.data.response.GameResponseResult
import io.reactivex.Single
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) : DataRepository {
    override fun getData(): Single<GameResponseResult> {
        return apiService.getData()
    }

    override fun getNextUrl(url: String): Single<GameResponseResult> {
        return apiService.getDataNext(url)
    }

    override fun getDatasBySearch(search: String): Single<GameResponseResult> {
        return apiService.getDataBySearch(search = search)
    }
}