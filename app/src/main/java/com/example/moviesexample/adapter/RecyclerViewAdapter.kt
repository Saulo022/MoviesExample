package com.example.moviesexample.adapter

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.moviesexample.R
import com.example.moviesexample.data.Movie
import com.example.moviesexample.data.MovieList
import com.example.moviesexample.databinding.RecyclerListRowBinding
import com.example.moviesexample.db.TMDBDatabase
import com.example.moviesexample.viewModel.MovieViewModel
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(movieViewModel: MovieViewModel) : PagingDataAdapter<Movie, RecyclerViewAdapter.MyViewHolder>(diffCallback){

    var onItemClick : ((Movie) -> Unit)? = null

    lateinit var favMovies :MutableList<Int>

    private var movieFav = movieViewModel.movieFavList

    var mov: Movie? = movieViewModel.movies

    private lateinit var context:Context

    val room by lazy {
        Room.databaseBuilder(
            context, TMDBDatabase::class.java, "popular_movies").build()
    }


    inner class MyViewHolder(
        val binding: RecyclerListRowBinding
    ) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecyclerListRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)




        holder.binding.apply {


            holder.itemView.apply {
                tvTitle.text = "${movie?.title}"
                tvDesc.text = "${movie?.overview}"

                Log.d(ContentValues.TAG, "EYYYYYYY" + movieFav.size)
                //if(movie?.id == mov?.id){ favIcon.isChecked = true}

                for (item in movieFav){
                    if (item == movie?.id){
                        favIcon.isChecked = true
                    }
                }

                val url = "https://image.tmdb.org/t/p/w780/${movie?.poster_path}"

                Picasso.get()
                    .load(url)
                    .fit()
                    .into(imageThumb)
            }
        }

        holder.itemView.setOnClickListener {
            if (movie != null) {
                onItemClick?.invoke(movie)
            }
        }
    }

    fun setData(data: List<Int>){
        favMovies.apply {
            clear()
            addAll(data)
        }
    }
}