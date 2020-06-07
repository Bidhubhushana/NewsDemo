package com.example.myapplication.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.db.NewsFeedModelEntity
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class DataBindingUtil {

    companion object {
        @JvmStatic
        @BindingAdapter("imgUrl")
        fun setImageUrl(imageView: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(imageView)
                    .load(url)
                    .into(imageView)
            }
        }

        @JvmStatic
        @BindingAdapter("text")
        fun setText(textView: TextView, text: String?) {
            if (!text.isNullOrEmpty()) {
                textView.text = text
            }
        }

        @JvmStatic
        @BindingAdapter("setTime")
        fun setTime(textView: TextView, text: String?) {

            if (!text.isNullOrEmpty()) {
                var calender=Calendar.getInstance(TimeZone.getTimeZone(text))
                val prettyTime = PrettyTime(Locale.getDefault())
                val ago: String = prettyTime.format(calender)
                if (!ago.isEmpty()) {
                    textView.text = ago
                }
            }
        }


        @JvmStatic
        @BindingAdapter("feedItems")
        fun setFeedItems(recyclerView: RecyclerView, items: LiveData<List<NewsFeedModelEntity?>>) {
            val adapter = recyclerView.adapter as NewsAdapter?
            if (adapter != null && !items.value.isNullOrEmpty()) {
                adapter.addItems(items.value!!)
            }
        }
    }
}