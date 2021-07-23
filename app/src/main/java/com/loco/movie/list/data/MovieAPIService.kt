package com.loco.movie.list.data

import com.loco.movie.list.data.models.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPIService {
    @GET("/")
    suspend fun fetchMovieResultsData(
        @Query("apikey") apikey: String,
        @Query("s") searchTerm: String,
        @Query("page")pageNo: Int
    ): MovieListResponse?
}