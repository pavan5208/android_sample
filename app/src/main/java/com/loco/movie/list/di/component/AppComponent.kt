package com.loco.movie.list.di.component

import android.app.Application
import com.loco.movie.MovieListApplication
import com.loco.movie.list.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class]
)
interface AppComponent: AndroidInjector<MovieListApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: MovieListApplication)

}