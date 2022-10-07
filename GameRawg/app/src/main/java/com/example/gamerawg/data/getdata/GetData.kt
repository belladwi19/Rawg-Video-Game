package com.example.gamerawg.data.getdata

import com.example.gamerawg.data.repository.DataRepository
import com.example.gamerawg.data.response.GameResponseResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetData @Inject constructor(private val repository: DataRepository) {
    operator fun invoke(): Single<GameResponseResult> {
        return repository.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}