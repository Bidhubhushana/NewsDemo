package com.example.myapplication.di

import androidx.room.RoomDatabase
import com.example.myapplication.db.AppDataBaseManager
import com.example.myapplication.db.NewsFeedModelEntity
import com.example.myapplication.manager.Retrofit2Manager
import com.example.myapplication.repo.AppRepository
import com.example.myapplication.util.CommonUtils
import com.example.myapplication.viewmodel.NewsFeedViewModel
import org.koin.dsl.module

val appModule= module {

        single { Retrofit2Manager }
        single { AppDataBaseManager }
        single { CommonUtils }
        factory {NewsFeedModelEntity()}
        factory { AppRepository(get(),get()) }
        factory { NewsFeedViewModel(get())}
}