package com.example.myapplication

import android.app.Application
import android.content.Context
import com.example.myapplication.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainApp :Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        initiateKoin()

    }


    fun getAppContext(): Context {
        return context
    }

    private fun initiateKoin() {
        startKoin{
            androidContext(this@MainApp)
            modules(appModule)
        }
    }
    companion object {
        lateinit var context: Context
        val apiKey :String = "5d5447ad1dd346c8a39affed015d98a1"
    }
}