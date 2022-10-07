package com.example.gamerawg.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamerawg.data.getdata.GetDataBySearch
import com.example.gamerawg.data.getdata.GetDataNext
import com.example.gamerawg.data.model.GameList
import com.example.gamerawg.data.response.GameResponseResult
import com.example.gamerawg.data.response.gameListing
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getDataNext: GetDataNext,
    private val getDataBySearch: GetDataBySearch
) : ViewModel() {

    val list = MutableLiveData<ArrayList<GameList>>()
    val platform = MutableLiveData<ArrayList<GameList>>()
    val itemPosition = MutableLiveData<Int>()
    val progressBar = MutableLiveData<Boolean>()
    val disposable = CompositeDisposable()
    val loading = MutableLiveData(false)
    val search = MutableLiveData("")
    val nextUrl = MutableLiveData("")
    val isFiltered = MutableLiveData(false)

    init {
        list.value = arrayListOf()
        platform.value = arrayListOf()
    }

    fun setSearchValues(searchStr: String?) {
        search.value = searchStr ?: ""
        isFiltered.value = true
    }

    fun getList() {
        progressBar.value = true
        itemPosition.value?.let {
        } ?: getDatasBySearch()
    }

    fun getDatasNext(context: Context) {
        progressBar.value = true
        nextUrl.value?.let {
            getDatasNextPage(context)
        } ?: kotlin.run {
            loading.value = true
            loading.value = false
            Toast.makeText(context, "Tidak ada yang dapat ditampilkan", Toast.LENGTH_SHORT)
                .show()
            progressBar.value = false
        }
    }

    private fun getDatasNextPage(context: Context) {
        disposable.add(getDataNext(nextUrl.value!!)
            .subscribeWith(object : DisposableSingleObserver<GameResponseResult>() {
                override fun onSuccess(t: GameResponseResult) {
                    t.gameListing().let {
                        loading.value = true
                        nextUrl.value = it.first
                        list.value!!.addAll(it.second)
                        loading.value = false
                        progressBar.value = false
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d(
                        "APIERROR",
                        e.localizedMessage ?: e.message
                        ?: "Error saat menampilkan data"
                    )
                    getDatasNextPage(context)
                }
            })
        )
    }

    private fun getDatasBySearch() {
        disposable.add(getDataBySearch(search.value ?: "")
            .subscribeWith(object : DisposableSingleObserver<GameResponseResult>() {
                override fun onSuccess(t: GameResponseResult) {
                    t.gameListing().let {
                        loading.value = true
                        nextUrl.value = it.first
                        list.value = it.second!!
                        loading.value = false
                        progressBar.value = false
                    }
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

    fun clearSearch() {
        search.value = ""
        if (itemPosition.value == -1) {
            isFiltered.value = false
        }
    }
}