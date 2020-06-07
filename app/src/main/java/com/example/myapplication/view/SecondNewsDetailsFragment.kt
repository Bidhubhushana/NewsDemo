package com.example.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.DetailsFragmentBinding
import com.example.myapplication.util.getFont
import com.example.myapplication.viewmodel.NewsFeedViewModel


class SecondNewsDetailsFragment : Fragment() {

    private lateinit var viewBinding: DetailsFragmentBinding
    private var position: Int = 0
    private  val newsFeedViewModel : NewsFeedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        position=SecondNewsDetailsFragmentArgs.fromBundle(requireArguments()).position
        viewBinding= DetailsFragmentBinding.inflate(inflater,container,false)


        return viewBinding.root
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setFonts()
        viewBinding.lifecycleOwner=this

        viewBinding.backImage.setOnClickListener {
            val backStack=SecondNewsDetailsFragmentDirections.actionsToNewsHeadline()
            findNavController().navigate(backStack)
        }

        newsFeedViewModel.getFeedList()?.observe(requireActivity(), Observer {
            if(!it.isNullOrEmpty()) {
                viewBinding.data=it[position]
            }
        })

        viewBinding.executePendingBindings()
    }

    private fun setFonts() {

        viewBinding.feedTittle.typeface = activity getFont getString(R.string.roboto_bold)
        viewBinding.feedSource.typeface = activity getFont getString(R.string.roboto_bold)
        viewBinding.feedDate.typeface = activity getFont getString(R.string.roboto_bold)
        viewBinding.newsDescription.typeface = activity getFont getString(R.string.roboto_bold)

    }

}


