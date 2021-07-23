package com.loco.movie.list.di.modules

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.loco.movie.list.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [ActivityBindingModule::class,
                   ViewModelBindingModule::class,
                    NetworkModule::class])
abstract class AppModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
   @Binds
    abstract fun bindContext(application: Application): Context
}