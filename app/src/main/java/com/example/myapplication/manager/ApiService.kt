package com.example.myapplication.manager

import com.example.myapplication.pojo.NewsHeadline
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface ApiService {


    @GET
    fun getNews(@Url url:String,page:Int): Call<NewsHeadline>

    @GET("top-headlines?country=in")
    fun getHeadLines(
        @Query("apiKey") site: String?,
        @Query("page") page: Int
    ): Call<NewsHeadline>?

}