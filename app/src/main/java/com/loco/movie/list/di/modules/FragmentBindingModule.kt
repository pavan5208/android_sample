package com.loco.movie.list.di.modules

import com.loco.movie.list.ui.fragments.MovieListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun injectMovieListFragment(): MovieListFragment
}