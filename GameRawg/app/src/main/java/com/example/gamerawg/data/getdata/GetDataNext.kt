package com.example.gamerawg.data.getdata

import com.example.gamerawg.data.response.GameResponseResult
import com.example.gamerawg.data.repository.DataRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetDataNext @Inject constructor(private val repository: DataRepository) {
    operator fun invoke(nextURL: String): Single<GameResponseResult> {
        return repository.getNextUrl(nextURL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}