package com.example.myapplication.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface NewsDao {

    @Query("SELECT * from news_feed ")
    fun getNewsFeed(): DataSource.Factory<Int, NewsFeedModelEntity>

    @Query("SELECT * FROM news_feed LIMIT :limit OFFSET :offset")
    fun getNewsFeedByPage(limit:Int,offset:Int):LiveData<List<NewsFeedModelEntity>>

    @Query("SELECT * from news_feed WHERE _id==:id")
    fun getNewsDetails(id:Long ):LiveData<NewsFeedModelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: NewsFeedModelEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<NewsFeedModelEntity?>)

    @Query("DELETE from news_feed")
    fun deleteAll()
}