package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.MainActivityBinding
import com.example.myapplication.viewmodel.NewsFeedViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityBinding:MainActivityBinding
    private  val newsViewModel:NewsFeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
    }
}
