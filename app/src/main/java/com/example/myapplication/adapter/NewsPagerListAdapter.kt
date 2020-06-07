package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemNewsBinding
import com.example.myapplication.db.NewsFeedModelEntity
import com.example.myapplication.util.getFont

class NewsPagerListAdapter(val context:Context,val listener:OnClick): PagedListAdapter<NewsFeedModelEntity, RecyclerView.ViewHolder>(
    diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val liveDataViewBinding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        liveDataViewBinding.title.typeface = context getFont "roboto_regular.ttf"
        liveDataViewBinding.sourceText.typeface = context getFont "roboto_bold.ttf"

        return NewsViewHolder(liveDataViewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as NewsViewHolder
        holder.view.data = getItem(position)
        holder.view.executePendingBindings()

        holder.view.itemCardView.setOnClickListener {
            listener.onClick(holder.view.image,position)
//            newsFeedViewModel.selectedFeedPosition.value = position

            //newsFeedViewModel.selectedFeedData?.value = newsList!![position]
        }
    }

    inner class NewsViewHolder(val view: ItemNewsBinding) : RecyclerView.ViewHolder(view.root)

    interface OnClick {
        fun onClick(imageView: ImageView, position: Int)
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<NewsFeedModelEntity>() {
            override fun areItemsTheSame(oldItem: NewsFeedModelEntity, newItem: NewsFeedModelEntity): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: NewsFeedModelEntity, newItem: NewsFeedModelEntity): Boolean =
                oldItem.equals(newItem.title)
        }
    }

}