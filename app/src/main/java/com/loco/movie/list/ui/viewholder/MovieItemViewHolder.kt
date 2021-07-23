package com.loco.movie.list.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loco.movie.list.R
import com.loco.movie.list.data.models.MovieData
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieItemViewHolder (private val clickListener: (MovieData) -> Unit,
                           itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setData(movieItem:MovieData){
        itemView.apply {
            this.setOnClickListener {
                clickListener.invoke(movieItem)
            }
            tv_movie_description?.text = ""
            tv_movie_name?.text = ""
            im_banner?.let {
                Glide.with(context)
                    .load(movieItem.imageURL)
                    .placeholder(R.drawable.placeholdeer_shows_grid)
                    .error(R.drawable.placeholdeer_shows_grid)
                    .into(im_banner)
            }
            tv_movie_name?.text = movieItem.movieName
            tv_movie_description?.text = movieItem.year
            //TODO raring is not provided so hard-coreded it
            tv_rating?.text = "4"
        }

    }
}