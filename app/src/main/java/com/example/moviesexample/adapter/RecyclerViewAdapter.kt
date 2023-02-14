package com.example.moviesexample.adapter

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesexample.R
import com.example.moviesexample.data.Movie
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(
    var movies: List<Movie>,
    private val movieClickedListener: (Movie) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){

    var items : List<Movie> = emptyList()

    var onItemClick : ((Movie) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setUpdatedData(items : List<Movie>) {
        this.items = items
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imageThumb = view.findViewById<ImageView>(R.id.imageThumb)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvDesc = view.findViewById<TextView>(R.id.tvDesc)

        val rvMovie = view.findViewById<RecyclerView>(R.id.recyclerView)

        fun bind(data : Movie) {
            tvTitle.text = data.title
            tvDesc.text = data.overview

            val url = "https://image.tmdb.org/t/p/w780/${data.poster_path}"

            Log.d(TAG, "my")

            Picasso.get()
                .load(url)
                .into(imageThumb)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_row, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items.get(position))
        var movie = items.get(position)


        holder.itemView.setOnClickListener { Log.d(TAG, "my Message")
            onItemClick?.invoke(movie)
         }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}