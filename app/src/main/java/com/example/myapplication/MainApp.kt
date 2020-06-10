package com.example.myapplication

import android.app.Application
import android.content.Context
import com.example.myapplication.di.*
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
            modules(listOf(appModule1, appmodule2, appmodule3, appmodule4, appmodule5, appmodule6))
        }
    }
    companion object {
        lateinit var context: Context
        val apiKey :String = "5d5447ad1dd346c8a39affed015d98a1"
    }
}