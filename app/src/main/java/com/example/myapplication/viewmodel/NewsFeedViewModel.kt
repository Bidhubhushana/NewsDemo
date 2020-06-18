package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.example.myapplication.db.AppDataBaseManager
import com.example.myapplication.db.NewsFeedModelEntity
import com.example.myapplication.repo.AppRepository
import com.example.myapplication.util.CommonUtils
import timber.log.Timber


class NewsFeedViewModel(val app: Application) : AndroidViewModel(app) {

    private val DATABASE_PAGE_SIZE: Int = 10
    private val PREFETCH_DISTANCE: Int = 5
    private val NETWORK_PAGE_SIZE = 10
    private var page: Int = 1
    private val newsFeedDao = AppDataBaseManager
    private val repository = AppRepository(app, this)
    var mIsMoreLoading: MutableLiveData<Boolean?> = MutableLiveData()
    val mIsLoading: MutableLiveData<Boolean?> = MutableLiveData()
    val mIsRefresh:MutableLiveData<Boolean?> = MutableLiveData()
    val misMoreDataAvailable: MutableLiveData<Boolean?> = MutableLiveData()

    private var newsFeed = LivePagedListBuilder(
        getAllNewsPagedFactory(), PagedList.Config.Builder()
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setPageSize(DATABASE_PAGE_SIZE)
            .setInitialLoadSizeHint(DATABASE_PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    ).setBoundaryCallback(RepoBoundaryCallback()).build()

    init {
        refreshFeed()
    }


    private fun getAllNewsPagedFactory(): DataSource.Factory<Int, NewsFeedModelEntity> {

        Timber.d("getAllReposPagedFactory(): ")
        return newsFeedDao.db.getNewsDao().getNewsFeed()

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


    fun refreshFeed(){
        page=1
        mIsRefresh.value=true
        mIsLoading.value = true
        mIsMoreLoading.value = false
        repository.updateNewsFeed(page)
    }

    inner class RepoBoundaryCallback : PagedList.BoundaryCallback<NewsFeedModelEntity>() {

        override fun onZeroItemsLoaded() {
            Timber.d("onZeroItemsLoaded(): ")
            CommonUtils.startRefreshingNewsScheduler(app)
        }

        override fun onItemAtEndLoaded(itemAtEnd: NewsFeedModelEntity) {
            Timber.d("onItemAtEndLoaded(): ")
            if (mIsMoreLoading.value!=null && mIsMoreLoading.value?.not()!!) {
                loadMore()
                mIsMoreLoading.value = true
            }
        }

    }
}