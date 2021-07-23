package com.loco.movie.list.data.models

import com.google.gson.annotations.SerializedName

data class MovieListResponse (
    @field:SerializedName("Response")
    val isSuccess: Boolean? = null,
    @field:SerializedName("Error")
    val errorMsg: String? = null,
    @field:SerializedName("totalResults")
    val totalResults: Int? = null,
    @field:SerializedName("Search")
    val searchResults: List<MovieData>? = null
)
data class MovieData (
    @field:SerializedName("Title")
    val movieName: String? = null,
    @field:SerializedName("Year")
    val year: String? = null,
    @field:SerializedName("imdbID")
    val imdbID: String? = null,
    @field:SerializedName("Type")
    val type: String? = null,
    @field:SerializedName("Poster")
    val imageURL: String? = null
)

