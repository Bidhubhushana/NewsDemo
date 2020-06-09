package com.example.myapplication.db
import androidx.room.Room
import com.example.myapplication.MainApp

object AppDataBaseManager {

    private const val DATABASE_NAME = "news_db"

    val db = Room.databaseBuilder(
        MainApp().getAppContext(),
        NewsDataBase::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()


}