package com.example.myapplication.di

import com.example.myapplication.db.AppDataBaseManager
import com.example.myapplication.db.NewsFeedModelEntity
import com.example.myapplication.manager.Retrofit2Manager
import com.example.myapplication.repo.AppRepository
import com.example.myapplication.util.CommonUtils
import com.example.myapplication.viewmodel.NewsFeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule1= module {
        viewModel {NewsFeedViewModel(app = get())}
}
val appmodule2= module {
        single { AppDataBaseManager }
}
val appmodule3= module {
        factory {NewsFeedModelEntity() }
}

val appmodule4= module {
        single { Retrofit2Manager }
}

val appmodule5= module {
        single { CommonUtils }
}

val appmodule6= module {
        factory { AppRepository(context = get(),model = get()) }
}

