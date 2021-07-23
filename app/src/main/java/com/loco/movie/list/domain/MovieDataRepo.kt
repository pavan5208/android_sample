package com.loco.movie.list.domain

import com.loco.movie.list.data.models.MovieListResponse
import com.loco.movie.list.domain.models.MovieListRequestData

interface MovieDataRepo {
    suspend fun fetchMovieResults(input: MovieListRequestData):Pair<MovieListResponse?,Exception?>
}