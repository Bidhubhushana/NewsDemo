package com.example.myapplication.repo

import android.app.Application
import android.content.Context
import com.example.myapplication.ApplicationContext
import com.example.myapplication.db.AppDataBaseManager
import com.example.myapplication.db.NewsFeedModelEntity
import com.example.myapplication.manager.ApiEndPoints
import com.example.myapplication.manager.ApiService
import com.example.myapplication.manager.Retrofit2Manager
import com.example.myapplication.pojo.NewsHeadline
import com.example.myapplication.pojo.NewsTittles
import com.example.myapplication.util.toastMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber


class AppRepository(val context: Context) : retrofit2.Callback<NewsHeadline> {

    private var newTitles: List<NewsTittles>? = null

    private val apiService = Retrofit2Manager.getRetrofit().create(ApiService::class.java)

    private var isMoreData:Boolean=false

    fun updateNewsFeed(page:Int) {
        val call = apiService.getHeadLines(ApiEndPoints.API_KEY,page)
        call?.enqueue(this)
    }
    fun loadMore(page: Int){
        val call = apiService.getHeadLines(ApiEndPoints.API_KEY,page)
        call?.enqueue(this)

    }

    override fun onFailure(call: Call<NewsHeadline>, t: Throwable) {

        Timber.d( "error==$t")
        context.toastMsg("No internet connection")
    }

    override fun onResponse(call: Call<NewsHeadline>, response: Response<NewsHeadline>) {

        //Timber.d("success==${response.body()?.articles}")
         Timber.d("url==${response.raw()}")
         //Timber.d("success==${response.body()?.status}")
        //Timber.d( "success==${response.body()?.totalResults}")


        newTitles = response.body()?.articles
        isMoreData = !newTitles.isNullOrEmpty()


        val newsList: MutableList<NewsFeedModelEntity> = ArrayList()

        newTitles?.forEach {
            val newFeedEntity = NewsFeedModelEntity()
            newFeedEntity.author = it.author
            newFeedEntity.content = it.content
            newFeedEntity.description = it.description
            newFeedEntity.publishedAt = it.publishedAt
            newFeedEntity.title = it.title
            newFeedEntity.url = it.url
            newFeedEntity.urlToImage = it.urlToImage
            newFeedEntity.source = it.source?.name

            newsList.add(newFeedEntity)
        }

        runBlocking {
            launch(Dispatchers.IO) {
                //AppDataBaseManager.db.getNewsDao().deleteAll()
                if (isMoreData){
                    AppDataBaseManager.db.getNewsDao().insertAll(newsList)
                }

            }
        }

    }


}