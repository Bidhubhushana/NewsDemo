package com.example.myapplication

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication


class ApplicationContext :Application() {

    override fun onCreate() {
        super.onCreate()
        context = this

    }


    fun getAppContext(): Context {
        return context
    }

    companion object {
        lateinit var context: Context
        val apiKey :String = "5d5447ad1dd346c8a39affed015d98a1"
    }
}