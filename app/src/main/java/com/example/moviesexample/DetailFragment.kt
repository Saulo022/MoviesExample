package com.example.moviesexample

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.moviesexample.data.Movie
import com.example.moviesexample.data.MovieList
import com.example.moviesexample.databinding.FragmentDetailBinding
import com.example.moviesexample.databinding.FragmentMovieBinding
import com.example.moviesexample.db.TMDBDatabase
import com.example.moviesexample.viewModel.MovieViewModel
import kotlinx.coroutines.launch


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


    private val sharedViewModel : MovieViewModel by activityViewModels()

    val room by lazy {
        Room.databaseBuilder(
            requireContext(), TMDBDatabase::class.java, "popular_movies"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val view = binding.root

        saveCheckBoxState()

        binding.favIcon.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) {
                updateDataToTrue()
                showToast("Item select")
            } else {
                updateDataToFalse()
                showToast("Item no selected")
            }
        }
        btnClicked()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide
            .with(this)
            .load("https://image.tmdb.org/t/p/w780/${sharedViewModel.movies?.backdrop_path}")
            .into(binding.backdrop)


        binding.Title.text = sharedViewModel.movies?.title
        binding.overview.text = sharedViewModel.movies?.overview
        sharedViewModel.movies?.let { bindDetailInfo(binding.detailInfo, it) }
        //Log.d(ContentValues.TAG, "my Message" + sharedViewModel.movies.title)
    }

    private fun bindDetailInfo(detailInfo: TextView, movie: Movie) {
        detailInfo.text = buildSpannedString {
            bold { append("Original title: ")}
            appendLine(movie.original_title)

            bold { append("Original language: ")}
            appendLine(movie.original_language)

            bold { append("Release date: ")}
            appendLine(movie.release_date)

            bold { append("Popularity: ")}
            appendLine(movie.popularity.toString())

            bold { append("Vote Average: ")}
            appendLine(movie.vote_average.toString())
        }
    }

    private fun showToast(str:String){
        Toast.makeText(context,str, Toast.LENGTH_SHORT).show()
    }

    private fun updateDataToTrue(){
        var id = sharedViewModel.id.value
        var movie = sharedViewModel.movies
        if (movie != null) {
            movie.video = true
        }

        lifecycleScope.launch {
            if (id != null) {
                if (movie != null) {
                    room.getMovieDao().updateFavMovies(movie)
                }
            }
            if (movie != null) {
                room.getMovieDao().updateFavMovies(movie)
            }
            room.getMovieDao().getAll()
            Log.d(ContentValues.TAG, "HOLAAAAAAAA" +room.getMovieDao().getAll() )
        }
        if (id != null) {
            Log.d(ContentValues.TAG, "HOLAAAAAAAA" + id)
            sharedViewModel.saveFavMovie(id)
        }
    }

    private fun updateDataToFalse(){
        var id = sharedViewModel.id.value
        var movie = sharedViewModel.movies
        if (movie != null) {
            movie.video = false
        }


        lifecycleScope.launch {
            if (id != null) {

                if (movie != null) {
                    room.getMovieDao().updateFavMovies(movie)
                }
            }
            if (movie != null) {
                room.getMovieDao().updateFavMovies(movie)
            }
        }
    }

    private fun btnClicked(){
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_favFragment)
        }
    }

    private fun saveCheckBoxState(){
        if (sharedViewModel.movies?.video == true){
            binding.favIcon.isChecked = true
        }
    }


}
