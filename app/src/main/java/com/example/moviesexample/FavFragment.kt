package com.example.moviesexample

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.moviesexample.adapter.FavAdapter
import com.example.moviesexample.adapter.RecyclerViewAdapter
import com.example.moviesexample.data.Movie
import com.example.moviesexample.databinding.FragmentFavBinding
import com.example.moviesexample.databinding.FragmentMovieBinding
import com.example.moviesexample.db.TMDBDatabase
import kotlinx.coroutines.launch


class FavFragment : Fragment() {

    private var _binding: FragmentFavBinding? = null
    private val binding: FragmentFavBinding get() = _binding!!

    private lateinit var recyclerViewAdapter : FavAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        val view = binding.root

        initRecyclerview()


        return view
    }

    private fun initRecyclerview() {

        lifecycleScope.launch {
            val room =
                this@FavFragment.context?.let { Room.databaseBuilder(
                    it, TMDBDatabase::class.java, "popular_movies").build() }

            var movieFavList = room?.getMovieDao()?.getAll()


        binding.apply {
            recyclerViewAdapter = FavAdapter()

            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(this@FavFragment.context)
                adapter = FavAdapter().apply {
                    if (movieFavList != null) {
                        setData(movieFavList)
                    }
                }
                }
            }
        }
    }


/*
    private fun loadData(){
        val room =
            this@FavFragment.context?.let { Room.databaseBuilder(
                it, TMDBDatabase::class.java, "popular_movies").build() }

        var movieList = listOf<Movie>()
        lifecycleScope.launch {
            if (room != null) {
                movieList = room.getMovieDao().getAll()
            }
            binding.tvTitle.setText(movieList[0].title)
        }
    }
 */
}