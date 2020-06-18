package com.example.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.example.myapplication.R
import com.example.myapplication.databinding.DetailsFragmentBinding
import com.example.myapplication.util.getFont
import com.example.myapplication.viewmodel.NewsFeedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SecondNewsDetailsFragment : Fragment() {

    private lateinit var viewBinding: DetailsFragmentBinding
    private var position: Int = 0
    private  val newsFeedViewModel : NewsFeedViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        position=SecondNewsDetailsFragmentArgs.fromBundle(requireArguments()).position
        viewBinding= DetailsFragmentBinding.inflate(inflater,container,false)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        return viewBinding.root
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setFonts()
        viewBinding.lifecycleOwner=this

        viewBinding.backImage.setOnClickListener {
            findNavController().popBackStack()
        }

        newsFeedViewModel.getFeedList()?.observe(requireActivity(), Observer {
            if(!it.isNullOrEmpty()) {
                try {
                    viewBinding.data=it[position]
                }
                catch (e:Exception){
                    e.printStackTrace()
                }

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


