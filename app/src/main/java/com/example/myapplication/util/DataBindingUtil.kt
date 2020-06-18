package com.example.myapplication.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.adapter.NewsPagerListAdapter
import com.example.myapplication.db.NewsFeedModelEntity

class DataBindingUtil {

    companion object {
        @JvmStatic
        @BindingAdapter("imgUrl")
        fun setImageUrl(imageView: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(imageView)
                    .load(url)
                    .apply(RequestOptions.centerCropTransform())
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
                val time= text.substringBefore("T","moments ago")
                if (!time.isNullOrEmpty()) {
                    textView.text = time
                }
            }
        }


        @JvmStatic
        @BindingAdapter("feedItems")
        fun setFeedItems(recyclerView: RecyclerView, items: PagedList<NewsFeedModelEntity?>?) {
            val adapter = recyclerView.adapter as NewsPagerListAdapter?
            if (!items.isNullOrEmpty()) {
                adapter?.submitList(items)
            }
        }
        @JvmStatic
        @BindingAdapter("loader_visibility")
        fun loaderVisibility(loader: ContentLoadingProgressBar, status: Boolean) {

            if (status) {
                loader.visibility = View.VISIBLE
            } else {
                loader.visibility = View.GONE
            }

        }

        @JvmStatic
        @BindingAdapter("more_data_visibility")
        fun loadMoreVisibility(loader: ContentLoadingProgressBar, status: Boolean) {

            if (status) {
                loader.visibility = View.VISIBLE
            } else {
                loader.visibility = View.GONE
            }

        }
    }
}