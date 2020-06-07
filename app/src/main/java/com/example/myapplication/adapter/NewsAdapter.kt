package com.example.myapplication.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemLoadingBinding
import com.example.myapplication.databinding.ItemNewsBinding
import com.example.myapplication.db.NewsFeedModelEntity
import com.example.myapplication.util.getFont
import com.example.myapplication.viewmodel.NewsFeedViewModel


class NewsAdapter(
    val context: Context, val  newsFeedViewModel: NewsFeedViewModel, val listener: OnClick
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var newsList: MutableList<NewsFeedModelEntity?> ? = null

    private val ITEM_VIEW_TYPE = 0
    private val LOADING_VIEW_TYPE = 1

    init {
        newsList= ArrayList()
    }


    fun addItems(list: List<NewsFeedModelEntity?>) {
        newsList?.clear()
        newsList?.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

         if (viewType==LOADING_VIEW_TYPE){
            val loadMoreDataBinding=ItemLoadingBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
            return LoadingViewHolder(loadMoreDataBinding)
        }

        val liveDataViewBinding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        liveDataViewBinding.title.typeface = context getFont "roboto_regular.ttf"
        liveDataViewBinding.sourceText.typeface = context getFont "roboto_bold.ttf"

        return NewsViewHolder(liveDataViewBinding)
    }

    override fun getItemCount(): Int {
        return newsList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (newsList?.get(position) == null) LOADING_VIEW_TYPE else ITEM_VIEW_TYPE
    }


    inner class NewsViewHolder(val view: ItemNewsBinding) : RecyclerView.ViewHolder(view.root)

    inner class LoadingViewHolder(val loadView: ItemLoadingBinding) : RecyclerView.ViewHolder(loadView.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder){
            holder.view.data = newsList!![position]
            holder.view.executePendingBindings()

            holder.view.itemCardView.setOnClickListener {
                listener.onClick(holder.view.image,position)
//            newsFeedViewModel.selectedFeedPosition.value = position

                //newsFeedViewModel.selectedFeedData?.value = newsList!![position]
            }
        }
        else {
            holder as LoadingViewHolder
            holder.loadView.progressBar.visibility=View.VISIBLE
        }
    }

    interface OnClick {
        fun onClick(imageView: ImageView, position: Int)
    }


}
