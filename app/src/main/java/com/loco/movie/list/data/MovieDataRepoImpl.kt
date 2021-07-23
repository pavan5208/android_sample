package com.loco.movie.list.data

import com.loco.movie.list.data.models.MovieListResponse
import com.loco.movie.list.domain.MovieDataRepo
import com.loco.movie.list.domain.models.MovieListRequestData
import timber.log.Timber
import javax.inject.Inject

class MovieDataRepoImpl @Inject constructor(private val movieDataService:MovieAPIService): MovieDataRepo {
    override suspend fun fetchMovieResults(input: MovieListRequestData):Pair<MovieListResponse?,Exception?> {
        return  try {
                val response = movieDataService?.fetchMovieResultsData("9cfa95f8", if (input.searchTerm.isNullOrBlank()) "Lord" else input.searchTerm, input.pageNo)
                if (response?.isSuccess == true) {
                    Pair(response, null)
                } else {
                    val error:String = response?.errorMsg?:"Something went wrong"
                    Pair(null, Exception(error))
                }
        } catch (e: Exception) {
            Timber.e(e)
            Pair(null,e)
        }
    }

}