package com.example.myapplication.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myapplication.R
import com.example.myapplication.databinding.DetailsFragmentBinding
import com.example.myapplication.util.getFont
import com.example.myapplication.viewmodel.NewsFeedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SecondNewsDetailsFragment : Fragment() {

    private lateinit var viewBinding: DetailsFragmentBinding
    private var position:Int=0
    private var imageUrl:String ?=null
    private  val newsFeedViewModel : NewsFeedViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {



        position=SecondNewsDetailsFragmentArgs.fromBundle(requireArguments()).position
        imageUrl=SecondNewsDetailsFragmentArgs.fromBundle(requireArguments()).data

        viewBinding= DetailsFragmentBinding.inflate(inflater,container,false)


        return viewBinding.root
    }


    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setFonts()
        viewBinding.lifecycleOwner=this

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.shared_element_transition)

        viewBinding.feedImage.apply {
            this.transitionName=imageUrl
            startEnterTransitionAfterLoadingImage(imageUrl!!, this)
        }

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
    private fun startEnterTransitionAfterLoadingImage(imageAddress: String, imageView: ImageView) {
        Glide.with(this)
            .load(imageAddress)
            .dontAnimate()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(imageView)
    }
}


