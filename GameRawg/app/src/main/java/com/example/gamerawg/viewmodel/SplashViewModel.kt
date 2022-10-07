package com.example.gamerawg.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamerawg.data.getdata.GetDataBySearch
import com.example.gamerawg.data.getdata.GetData
import com.example.gamerawg.data.model.GameList
import com.example.gamerawg.data.response.GameResponseResult
import com.example.gamerawg.data.response.gameListing
import com.example.gamerawg.data.response.gamePlatform
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getData: GetData,
    private val getDataBySearch: GetDataBySearch
) : ViewModel() {
    private val disposable = CompositeDisposable()
    val list = MutableLiveData<ArrayList<GameList>>()
    val platform = MutableLiveData<ArrayList<GameList>>()
    val nextUrl = MutableLiveData<String>()
    val nextByList = MutableLiveData(false)
    val nextByPlatform = MutableLiveData(false)

    init {
        list.value = arrayListOf()
        platform.value = arrayListOf()
    }

    fun getFirstList() {
        disposable.add(
            getDataBySearch("")
                .subscribeWith(object : DisposableSingleObserver<GameResponseResult>() {
                    override fun onSuccess(t: GameResponseResult) {
                        t.gameListing().let {
                            list.value = it.second!!
                            nextUrl.value = it.first ?: ""
                        }
                        nextByList.value = true
                    }

                    override fun onError(e: Throwable) {
                        Log.d(
                            "APIERROR",
                            e.localizedMessage ?: e.message
                            ?: "Error saat menampilkan data"
                        )
                    }
                })
        )
    }

    fun getPlatforms() {
        disposable.add(
            getData()
                .subscribeWith(object : DisposableSingleObserver<GameResponseResult>() {
                    override fun onSuccess(t: GameResponseResult) {
                        platform.value = t.gamePlatform()
                        nextByPlatform.value = true
                    }

                    override fun onError(e: Throwable) {
                        Log.d(
                            "APIERROR",
                            e.localizedMessage ?: e.message
                            ?: "Error saat menampilkan data"
                        )
                    }
                })
        )
    }
}