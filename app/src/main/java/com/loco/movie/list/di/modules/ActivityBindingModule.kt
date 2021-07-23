package com.loco.movie.list.di.modules

import com.loco.movie.list.ui.activities.MovieListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBindingModule::class
        ]
    )
    abstract fun injectMovieListActivity():MovieListActivity
}