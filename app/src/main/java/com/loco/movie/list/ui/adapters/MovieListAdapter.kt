package com.loco.movie.list.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.loco.movie.list.R
import com.loco.movie.list.data.models.MovieData
import com.loco.movie.list.ui.viewholder.MovieItemViewHolder

class MovieListAdapter(private val onMovieClick: (MovieData) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var moviesList = ArrayList<MovieData>()

    fun setData(value: List<MovieData>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(moviesList, value))
        diffResult.dispatchUpdatesTo(this)
        moviesList.clear()
        value.forEach {
            moviesList.add(it.copy())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
     return  MovieItemViewHolder(onMovieClick, LayoutInflater.from(parent.context).inflate(
         R.layout.item_movie,
         parent,
         false)
     )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieItemViewHolder).setData(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    inner class DiffCallback(
        private val oldList: List<MovieData>,
        private val newList: List<MovieData>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.imdbID == newItem.imdbID
        }


        override fun getOldListSize(): Int {
            return oldList.size
        }


        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem && oldItem.year == newItem.year
        }

    }
}