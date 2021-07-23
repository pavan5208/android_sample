package com.loco.movie.list.ui.viewmodels

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loco.movie.list.data.models.MovieData
import com.loco.movie.list.data.models.MovieListResponse
import com.loco.movie.list.domain.MovieDataRepo
import com.loco.movie.list.domain.models.MovieListRequestData
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MovieListViewModel @Inject constructor(private val movieRepo: MovieDataRepo) : ViewModel() {

    private val jobDelegate = lazy { SupervisorJob() }
    private val job by jobDelegate
    protected val ioScope by lazy { CoroutineScope(job + Dispatchers.IO) }

    private val _isLoading by lazy { MutableLiveData<Boolean>() }
    val isLoading: LiveData<Boolean> by lazy { _isLoading }

    private val _error by lazy { MutableLiveData<String>() }
    val error: LiveData<String> by lazy { _error }

    private val _responseDataLD by lazy { MutableLiveData<MovieListResponse>() }
    val responseDataLD by lazy { _responseDataLD }

    private val _searchResultList by lazy { MutableLiveData<List<MovieData>>() }
    val searchResultList by lazy { _searchResultList }

    val movieListRequestInfo: MovieListRequestData by lazy {
        MovieListRequestData("", 1)
    }


    private var searchJob: Job? = null
    fun searchDebounced(searchText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            resetValues()
            movieListRequestInfo.searchTerm = searchText
            fetchData()
        }
    }
    fun fetchData() {

        _isLoading.postValue(true)
        ioScope.launch {
            movieRepo.fetchMovieResults(movieListRequestInfo).let {
                if (it.first != null) {
                   if(!it.first?.searchResults.isNullOrEmpty()){
                       if(movieListRequestInfo.pageNo ==1) {
                           _searchResultList.postValue(it.first!!.searchResults)
                       }else{
                           updateCurrentList(it.first!!.searchResults)
                       }
                   }else{
                       if(movieListRequestInfo.pageNo ==1) {
                           _searchResultList.postValue(emptyList())
                       }
                   }
                } else if (it.second != null) {
                    _error.postValue(it.second?.message?:"Something went wrong")
                }
                _isLoading.postValue(false)
            }
        }
    }

    private fun updateCurrentList(searchResults: List<MovieData>?) {
        if(!searchResults.isNullOrEmpty()){
            val list = searchResultList.value
            val tempResults =ArrayList<MovieData>()
            list?.let {
                tempResults.addAll(list)
            }
            searchResults.forEach { chatMessage ->
                tempResults.add(chatMessage)
            }

            _searchResultList.postValue(tempResults)
        }
    }

    fun fetchNextPageData() {
        movieListRequestInfo.pageNo += 1
        fetchData()
    }

    fun resetValues() {
        movieListRequestInfo.pageNo = 1
        movieListRequestInfo.searchTerm = ""
    }


    @CallSuper
    override fun onCleared() {
        if (jobDelegate.isInitialized()) {
            job.cancel()
        }
        super.onCleared()
    }

    /** Sorting can't be efficient because we have no id and if we use year we are getting 1997–, 2017-2020
     * so I took year?.substring(0,4) but sometimes it was given raising exceptions
     * and Ratings are not the part of response. Check below response object of year for more details
     * {"Title":"Spellbinder: Land of the Dragon Lord","Year":"1997–","imdbID":"tt0118476",
     * "Type":"series","Poster":"https://m.media-amazon.com/images/M/MV5BMWJjMTMyZjItOThmZC00MWIyLTgxYmItNzMzN2RhZmZhYTc0XkEyXkFqcGdeQXVyNTE1NjY5Mg@@._V1_SX300.jpg"}
     */

    fun sortData(isAscending: Boolean) {
        if(searchResultList.value?.size?:0>0){
            var tempList = searchResultList.value!!.toMutableList()
            tempList?.let {
                try {
                    if (isAscending) {
                        Collections.sort(tempList,
                            Comparator<MovieData?> { lhs, rhs ->
                                Integer.valueOf(lhs.year?.substring(0,4)?.toInt() ?: 0)
                                    .compareTo(rhs.year?.substring(0,4)?.toInt() ?: 0)
                            })
                    } else {
                        Collections.sort(tempList,
                            Comparator<MovieData?> { lhs, rhs ->
                                Integer.valueOf(rhs.year?.substring(0,4)?.toInt() ?: 0)
                                    .compareTo(lhs.year?.substring(0,4)?.toInt() ?: 0)
                            })
                    }
                }catch (ex:Exception){

                }
                finally {
                    _searchResultList.postValue(tempList)
                }
            }
        }
    }


}