package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemLoadingBinding
import com.example.myapplication.databinding.ItemNewsBinding
import com.example.myapplication.db.NewsFeedModelEntity
import com.example.myapplication.util.getFont
import com.example.myapplication.util.gone
import com.example.myapplication.util.visible
import com.example.myapplication.viewmodel.NewsFeedViewModel

class NewsPagerListAdapter(val context: Context, val listener: OnClick,val model:NewsFeedViewModel) :
    PagedListAdapter<NewsFeedModelEntity, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            ITEM_TYPE_NEWS -> {
                return NewsViewHolder(setUponCreateNewsViewModel(parent))
            }
            ITEM_TYPE_FOOTER -> {
                val footerBinding = ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return FooterViewHolder(footerBinding)
            }
            else -> {
                return NewsViewHolder(setUponCreateNewsViewModel(parent))
            }
        }

    }

    fun setUponCreateNewsViewModel(parent: ViewGroup):ItemNewsBinding {
        val liveDataViewBinding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        liveDataViewBinding.title.typeface = context getFont "roboto_regular.ttf"
        liveDataViewBinding.sourceText.typeface = context getFont "roboto_bold.ttf"
        return liveDataViewBinding
    }

    override fun getItemCount(): Int {
        return super.getItemCount()+1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount-1-> ITEM_TYPE_FOOTER
            else -> ITEM_TYPE_NEWS
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is NewsViewHolder->{

                val  item=getItem(position)
                holder.headerView.data = item
                holder.headerView.image.transitionName=item?.urlToImage!!
                holder.headerView.executePendingBindings()

                holder.headerView.itemCardView.setOnClickListener {
                    listener.onClick(holder.headerView.image, position,item)
                }
            }
            is FooterViewHolder->{

                model.mIsMoreLoading.observe(context as LifecycleOwner, Observer {
                    if (it!!) {
                        holder.footerView.progressBar.visible()
                    } else {
                        holder.footerView.progressBar.gone()
                    }
                })

            }

        }

    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(AdapterDataObserverProxy(observer, 1))
    }


    inner class NewsViewHolder(val headerView: ItemNewsBinding) :
        RecyclerView.ViewHolder(headerView.root)

    class FooterViewHolder(val footerView: ItemLoadingBinding) :
        RecyclerView.ViewHolder(footerView.root)

    interface OnClick {
        fun onClick(imageView: ImageView, position: Int,item:NewsFeedModelEntity?)
    }

    companion object {

        private const val ITEM_TYPE_NEWS = 99
        private const val ITEM_TYPE_FOOTER = 100

        private val diffCallback = object : DiffUtil.ItemCallback<NewsFeedModelEntity>() {
            override fun areItemsTheSame(
                oldItem: NewsFeedModelEntity,
                newItem: NewsFeedModelEntity
            ): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(
                oldItem: NewsFeedModelEntity,
                newItem: NewsFeedModelEntity
            ): Boolean =
                oldItem.equals(newItem.title)
        }
    }
}


