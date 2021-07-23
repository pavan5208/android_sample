package com.loco.movie.list.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.loco.movie.list.BuildConfig
import com.loco.movie.list.data.MovieAPIService
import com.loco.movie.list.data.MovieDataRepoImpl
import com.loco.movie.list.domain.MovieDataRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
abstract class NetworkModule {
    @Binds
    abstract  fun bindingMovieDataRepo(movieRepoImpl: MovieDataRepoImpl): MovieDataRepo

  companion object {
      @Provides
      fun provideOkHttpClient(context: Context, gson: Gson): OkHttpClient {
          val okHttpBuilder = OkHttpClient.Builder()
          if (BuildConfig.DEBUG) {
              val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.i(message) }
              loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
              okHttpBuilder.addInterceptor(loggingInterceptor)
          }
          okHttpBuilder.readTimeout(30, TimeUnit.SECONDS)
          okHttpBuilder.connectTimeout(30, TimeUnit.SECONDS)
          okHttpBuilder.writeTimeout(30, TimeUnit.SECONDS)


          return okHttpBuilder.build()
      }


      @Singleton
      @Provides
      fun provideGson(): Gson {
          return GsonBuilder()
              .setLenient()
              .create()
      }


      @Provides
      @Singleton
      fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
          return Retrofit.Builder()
              .baseUrl("http://www.omdbapi.com/")
              .client(okHttpClient)
              .addConverterFactory(GsonConverterFactory.create(gson))
              .build()
      }

      @Provides
      @Singleton
      fun provideMovieApiService(retrofit: Retrofit): MovieAPIService {
          return retrofit.create(MovieAPIService::class.java)
      }
  }

}