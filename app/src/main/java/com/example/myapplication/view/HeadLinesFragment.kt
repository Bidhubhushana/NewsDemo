package com.example.myapplication.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.NewsPagerListAdapter
import com.example.myapplication.databinding.HeadlinesFragmentBinding
import com.example.myapplication.util.getFont
import com.example.myapplication.viewmodel.NewsFeedViewModel


class HeadLinesFragment : Fragment(), NewsPagerListAdapter.OnClick {

    private  val newsFeedViewModel: NewsFeedViewModel by activityViewModels()
    private lateinit var headlineBinding:HeadlinesFragmentBinding
    private lateinit var newsAdapter: NewsPagerListAdapter
    private var isLoading:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        headlineBinding = HeadlinesFragmentBinding.inflate(inflater, container,false)

        return headlineBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        headlineBinding.viewModel = newsFeedViewModel
        headlineBinding.lifecycleOwner = this

        newsAdapter = NewsPagerListAdapter(activity as Context, this)
        headlineBinding.feedRecyclerView.adapter = newsAdapter

        newsFeedViewModel.getFeedList()?.observe(requireActivity(), Observer { it ->
               newsAdapter.submitList(it)
            })


        headlineBinding.headline.typeface = activity getFont getString(R.string.roboto_bold)

        initScrollListener()

        headlineBinding.executePendingBindings()
    }

    private fun initScrollListener() {
        headlineBinding.feedRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == newsAdapter.itemCount - 1) {
                        //bottom of list!
                        newsFeedViewModel.loadMore()
                        //newsFeedViewModel.notifyAdapter(newsAdapter.itemCount - 1,10)
                        isLoading = true
                    }
                }
            }
        })
    }



    override fun onClick(imageView: ImageView, position: Int) {
        val  action=HeadLinesFragmentDirections.actionsToNewsDetails()
       // val extras = FragmentNavigatorExtras(imageView to "img_transition")
        action.position=position
        findNavController().navigate(action)
    }

}
