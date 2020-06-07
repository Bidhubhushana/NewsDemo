package com.example.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [NewsFeedModelEntity::class],version = 1)

abstract class NewsDataBase:RoomDatabase() {

    abstract fun  getNewsDao(): NewsDao

}