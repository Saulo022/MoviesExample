package com.example.moviesexample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.moviesexample.adapter.FavAdapter.MyViewHolder
import com.example.moviesexample.data.Movie
import com.example.moviesexample.databinding.RecyclerLisFavBinding
import com.example.moviesexample.db.TMDBDatabase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class FavAdapter : RecyclerView.Adapter<FavAdapter.MyViewHolder>(){

    private var movieFav = mutableListOf<Movie>()
    var movies : MutableList<Movie> = ArrayList()
    lateinit var context:Context


    inner class MyViewHolder(
        val binding: RecyclerLisFavBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecyclerLisFavBinding.inflate
                (LayoutInflater.from(parent.context),parent,false)
        )
    }


    override fun getItemCount(): Int {
        return movieFav.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = movieFav[position]


        holder.binding.apply {

                tvTitle.text = item.title
                tvVote.text = item.vote_count.toString()

            val url = "https://image.tmdb.org/t/p/w780/${item.poster_path}"

            Picasso.get()
                .load(url)
                .fit()
                .into(ivAvatar)

        }
    }

    fun setData(data: List<Movie>){
        movieFav.apply {
            clear()
            addAll(data)
        }
    }
}