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

class NewsFeedViewModel(app: Application): AndroidViewModel(app) {

    private val isMoreData: MutableLiveData<Boolean> = MutableLiveData()
    private var pageSize:MutableLiveData<Int> = MutableLiveData()
    private val repository: AppRepository = AppRepository(app)
    private var page:Int=1
    private var initialPageSize=10
    private var newsFeedDao= AppDataBaseManager.db.getNewsDao()
    private var newsFeed: LiveData<PagedList<NewsFeedModelEntity>> ? =null
    private var utils=CommonUtils

    init {
        page=1
        isMoreData.value=false
        pageSize.value=initialPageSize
        repository.updateNewsFeed(page = page)
        newsFeed = newsFeedDao.getNewsFeed().toLiveData(Config(pageSize = pageSize.value!!,enablePlaceholders = true, maxSize = 200))
        utils.startRefreshingNewsScheduler(app)
    }

    fun loadMore(){
        page++
        pageSize.value=initialPageSize+10
        isMoreData.value=true
        repository.loadMore(page = page)

    }

    fun getFeedList():LiveData<PagedList<NewsFeedModelEntity>> ?{
        return newsFeed
    }


}