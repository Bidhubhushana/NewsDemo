package com.example.myapplication.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.db.AppDataBaseManager
import com.example.myapplication.repo.AppRepository
import org.koin.core.KoinComponent
import java.lang.Exception

class RefreshNewsWorkManager(val context: Context, val params: WorkerParameters) :
    Worker(context, params),KoinComponent {

     private var appRespo = AppRepository(context,null)

    override fun doWork(): Result {
        var isSuccess: Boolean
        try {
            AppDataBaseManager.db.getNewsDao().deleteAll()
            appRespo.updateNewsFeed(page = 1)
            isSuccess = true
        } catch (e: Exception) {
            isSuccess = false
            e.printStackTrace()
        }
        if (isSuccess)
            return Result.success()
        else
            return Result.failure()
    }
}