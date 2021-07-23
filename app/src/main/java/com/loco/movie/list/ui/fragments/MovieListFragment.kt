package com.loco.movie.list.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.loco.movie.list.R
import com.loco.movie.list.data.models.MovieData
import com.loco.movie.list.ui.adapters.MovieListAdapter
import com.loco.movie.list.ui.viewmodels.MovieListViewModel
import com.loco.movie.list.utils.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.android.synthetic.main.layout_zero_case.*
import javax.inject.Inject

class MovieListFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val movieListViewModel: MovieListViewModel by viewModels {
        viewModelFactory
    }

    lateinit var scrollListener: EndlessRecyclerOnScrollListener
    lateinit var gridLayoutManager:GridLayoutManager

    private val moviesAdapter by lazy { MovieListAdapter(::onMovieClicked) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
      val rootView =  inflater.inflate(R.layout.fragment_movies_list, container, false)
      return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeViewModel()
    }

    private fun setUpViews() {
        gridLayoutManager = GridLayoutManager(requireContext(), 2)
        rv_movies_list?.apply {
            layoutManager = gridLayoutManager
            adapter = moviesAdapter
        }
        scrollListener = object : EndlessRecyclerOnScrollListener(gridLayoutManager) {
            override fun onLoadMore(current_page: Int) {
                movieListViewModel.fetchNextPageData()
            }
        }
        scrollListener.setMaxDataSizePerPage(10)
        scrollListener.resetRecylerView()
        rv_movies_list?.addOnScrollListener(scrollListener)
        movieListViewModel?.fetchData()
        initListeners()
    }

    private fun initListeners() {
        im_grid_switch?.setOnClickListener {
            if(im_grid_switch.tag == "grid") {
                im_grid_switch.tag = "list"
                gridLayoutManager.spanCount = 1
                im_grid_switch.setImageResource(R.drawable.ic_gride)
            }else{
                im_grid_switch.tag = "grid"
                gridLayoutManager.spanCount = 2
                im_grid_switch.setImageResource(R.drawable.ic_list)
            }
            moviesAdapter?.notifyItemRangeChanged(0, moviesAdapter?.itemCount ?: 0)
        }
        im_sort?.setOnClickListener {
            if(im_sort.tag == "asc") {
                im_sort.rotation = 180.0f
                im_sort.tag = "dsc"
                movieListViewModel.sortData(false)
            }else{
                im_sort.rotation = 360.0f
                im_sort.tag = "asc"
                movieListViewModel.sortData(true)
            }
        }
       im_cancel?.setOnClickListener {
          loadFreshData()
       }
        edt_search?.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.trim()?.length?:0>0) {
                    movieListViewModel.searchDebounced(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun loadFreshData() {
        movieListViewModel.resetValues()
        movieListViewModel.fetchData()
        edt_search?.setText("")
        resetSrollListener()
    }

    private fun onMovieClicked(movieData: MovieData) {
      requireContext().toast("Clicked on ${movieData.movieName}")
    }

    private fun observeViewModel() {

        movieListViewModel.isLoading.observe(viewLifecycleOwner,  {
            it?.let {
                showOrHideLoading(it)
            }
        })

        movieListViewModel.searchResultList.observe(viewLifecycleOwner,  {
            resetSrollListener()
            if(!it.isNullOrEmpty()){
                moviesAdapter.setData(it)
                showOrHideContent(true)
                hideEmptyView()
                showOrHideLoading(false)
                /** There might be scroll issue while user is on page 1 so smooth scrolling to position 0
                 */
                if(movieListViewModel.movieListRequestInfo.pageNo == 1){
                    rv_movies_list?.postDelayed({
                        rv_movies_list?.smoothScrollToPosition(0)
                    },300)
                }
            }else{
                showOrHideContent(false)
                showEmptyView()
            }
        })

        movieListViewModel.error.observe(viewLifecycleOwner,  {
            it?.let {
               showErrorView(it)
            }
        })

    }

    private fun resetSrollListener() {
        if(movieListViewModel.movieListRequestInfo.pageNo == 1){
            scrollListener?.resetRecylerView()
        }
    }

    private fun showErrorView(it: String) {
        var errorMSg  =""
        if(!requireContext().isNetworkAvailable()){
            errorMSg = resources.getString(R.string.network_error)
        }else if(!it.isNullOrBlank()){
            errorMSg = it
        }else{
            errorMSg = resources.getString(R.string.something_wrong)
        }
        if(movieListViewModel.movieListRequestInfo.pageNo == 1) {
            resetSrollListener()
            showOrHideContent(false)
            tv_zero_case?.text = errorMSg
            btn_retry?.show()
            empty_layout?.show()
            btn_retry?.setOnClickListener {
                btn_retry?.remove()
                empty_layout?.remove()
                loadFreshData()
            }
        }else{
            requireContext().toast(errorMSg)
        }

    }

    private fun showOrHideContent(status: Boolean) {
        if(status){
            im_grid_switch?.show()
            im_sort?.show()
            rv_movies_list?.show()
        }else{
            im_grid_switch?.remove()
            rv_movies_list?.remove()
            im_sort?.remove()
        }
    }

    private fun showEmptyView() {
        tv_zero_case?.text = resources.getString(R.string.no_results)
        btn_retry?.remove()
        empty_layout?.show()
    }
    private fun hideEmptyView() {
        empty_layout?.remove()
    }

    private fun showOrHideLoading(status: Boolean) {
        if(status){
            if(movieListViewModel.movieListRequestInfo.pageNo > 1){
                progress_bottom?.show()
            }else{
                showOrHideContent(false)
                loader_layout?.show()
            }
        }else{
            loader_layout?.remove()
            progress_bottom?.remove()
        }
    }
}