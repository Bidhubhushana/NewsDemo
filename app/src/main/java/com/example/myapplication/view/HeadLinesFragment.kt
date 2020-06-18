package com.example.myapplication.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.adapter.NewsPagerListAdapter
import com.example.myapplication.databinding.HeadlinesFragmentBinding
import com.example.myapplication.util.getFont
import com.example.myapplication.viewmodel.NewsFeedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.KoinComponent



class HeadLinesFragment : Fragment(),KoinComponent, NewsPagerListAdapter.OnClick {

    private val newsFeedViewModel: NewsFeedViewModel by sharedViewModel()
    private lateinit var headlineBinding: HeadlinesFragmentBinding
    private lateinit var newsAdapter: NewsPagerListAdapter
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        headlineBinding = HeadlinesFragmentBinding.inflate(inflater, container, false)

        return headlineBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        headlineBinding.viewModel = newsFeedViewModel
        headlineBinding.lifecycleOwner = this

        newsAdapter = NewsPagerListAdapter(activity as Context, this,newsFeedViewModel)
        headlineBinding.feedRecyclerView.adapter = newsAdapter


        newsFeedViewModel.getFeedList()?.observe(requireActivity(), Observer { it ->
            newsAdapter.submitList(it)
        })

        newsFeedViewModel.mIsRefresh.observe(requireActivity(), Observer {
            headlineBinding.SwipeToRefreshLayout.isRefreshing = it!!
        })

        headlineBinding.SwipeToRefreshLayout.setOnRefreshListener {
            newsFeedViewModel.refreshFeed()
        }

        headlineBinding.headline.typeface = activity getFont getString(R.string.roboto_bold)

        headlineBinding.executePendingBindings()
    }


    override fun onClick(imageView: ImageView, position: Int) {
         val action = HeadLinesFragmentDirections.actionsToNewsDetails()
         val extras = FragmentNavigatorExtras(imageView to "img_transition")
         action.position = position
        findNavController().navigate(action,extras)
    }

}

