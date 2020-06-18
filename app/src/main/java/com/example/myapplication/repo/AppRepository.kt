package com.example.myapplication.repo

import android.app.Application
import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.db.AppDataBaseManager
import com.example.myapplication.db.NewsFeedModelEntity
import com.example.myapplication.manager.ApiEndPoints
import com.example.myapplication.manager.ApiService
import com.example.myapplication.manager.Retrofit2Manager
import com.example.myapplication.pojo.NewsHeadline
import com.example.myapplication.pojo.NewsTittles
import com.example.myapplication.util.toastMsg
import com.example.myapplication.viewmodel.NewsFeedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber


class AppRepository(val context: Context, val model: NewsFeedViewModel?) : retrofit2.Callback<NewsHeadline> {


    private var newTitles: List<NewsTittles>? = null

    private val apiService = Retrofit2Manager.getRetrofit().create(ApiService::class.java)


    fun updateNewsFeed(page:Int) {
        val call = apiService.getHeadLines(ApiEndPoints.API_KEY,page)
        call?.enqueue(this)
    }
    fun loadMore(page: Int){
        val call = apiService.getHeadLines(ApiEndPoints.API_KEY,page)
        call?.enqueue(this)

    }

    override fun onFailure(call: Call<NewsHeadline>, t: Throwable) {

        model?.mIsLoading?.postValue(false)
        model?.mIsMoreLoading?.postValue(false)
        Timber.d( "error==$t")
        context.toastMsg(context.getString(R.string.no_interner))
    }

    override fun onResponse(call: Call<NewsHeadline>, response: Response<NewsHeadline>) {

        //Timber.d("success==${response.body()?.articles}")
         Timber.d("url==${response.raw()}")
         //Timber.d("success==${response.body()?.status}")
        //Timber.d( "success==${response.body()?.totalResults}")
        model?.mIsLoading?.postValue(false)
        model?.mIsMoreLoading?.postValue(false)
        model?.mIsRefresh?.postValue(false)

        if (response.body()?.articles.isNullOrEmpty()){
            model?.misMoreDataAvailable?.postValue(false)
        }
        else{
            model?.misMoreDataAvailable?.postValue(true)
        }

        if (response.code()==200){

            newTitles = response.body()?.articles

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

                    if (model?.mIsMoreLoading?.value!=null && model.mIsMoreLoading.value?.not()!!){
                        AppDataBaseManager.db.getNewsDao().deleteAll()
                    }
                    AppDataBaseManager.db.getNewsDao().insertAll(newsList)


                }
            } 
        }
        else{
            context.toastMsg(context.getString(R.string.oops_wrong))
        }
    }


}