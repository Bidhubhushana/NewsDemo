package com.example.myapplication.util

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.Toast
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.workmanager.RefreshNewsWorkManager
import java.util.concurrent.TimeUnit


infix fun Context?.getFont(fontName: String) = Typeface.createFromAsset(this?.assets, fontName)

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun Context?.toastMsg(msg: String) {
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}


class CommonUtils {

    companion object{

        private const val REFRESH_NES_FEED="refreshing"

        fun startRefreshingNewsScheduler(context: Context) {

            val workManager = WorkManager.getInstance(context)
            val workRequest =
                PeriodicWorkRequest.Builder(RefreshNewsWorkManager::class.java,
                    2, TimeUnit.HOURS)
                    .build()
                workManager.enqueueUniquePeriodicWork(
                REFRESH_NES_FEED, ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )

        }

    }
}