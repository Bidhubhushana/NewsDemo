package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.myapplication.db.AppDataBaseManager
import com.example.myapplication.db.NewsFeedModelEntity
import com.example.myapplication.repo.AppRepository
import com.example.myapplication.util.CommonUtils


class NewsFeedViewModel(val app: Application) : AndroidViewModel(app) {

    private var page: Int = 1
    private val newsFeedDao = AppDataBaseManager
    private val repository = AppRepository(app,this)
    private var newsFeed: LiveData<PagedList<NewsFeedModelEntity>>? = null
    var mIsMoreLoading: MutableLiveData<Boolean?> = MutableLiveData()
    val mIsLoading: MutableLiveData<Boolean?> = MutableLiveData()
    val misMoreDataAvailable: MutableLiveData<Boolean?> = MutableLiveData()
    private val utils=CommonUtils

    init {

        page = 1
        mIsLoading.value = true
        mIsMoreLoading.value = false
        repository.updateNewsFeed(page = page)
        newsFeed = newsFeedDao.db.getNewsDao().getNewsFeed().toLiveData(
            Config(
                pageSize = 2,
                enablePlaceholders = true,
                maxSize = 100
            )
        )
        utils.startRefreshingNewsScheduler(app)
    }

    fun loadMore() {
        if (misMoreDataAvailable.value == false) {
            mIsMoreLoading.value = false
            return
        }
        page++
        mIsMoreLoading.value = true
        repository.loadMore(page = page)
    }

    fun getFeedList(): LiveData<PagedList<NewsFeedModelEntity>>? {
        return newsFeed
    }
}