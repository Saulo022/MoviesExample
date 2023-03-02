package com.example.moviesexample

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.moviesexample.adapter.LoaderStateAdapter
import com.example.moviesexample.adapter.RecyclerViewAdapter
import com.example.moviesexample.data.Movie
import com.example.moviesexample.data.MovieList
import com.example.moviesexample.databinding.FragmentMovieBinding
import com.example.moviesexample.databinding.RecyclerListRowBinding
import com.example.moviesexample.db.TMDBDatabase
import com.example.moviesexample.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import hilt_aggregated_deps._dagger_hilt_android_internal_modules_ApplicationContextModule
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
class MovieFragment : Fragment() {

    //@Inject
    //lateinit var factory: ViewModelFactory
    //private lateinit var movieViewModel: MyViewModel

    private var _binding: FragmentMovieBinding? = null
    private val binding: FragmentMovieBinding get() = _binding!!



    private val viewModel: MovieViewModel by viewModels()
    private val sharedViewModel: MovieViewModel by activityViewModels()

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    private var movieFav = mutableListOf<Int>()

    lateinit var checkBox : CheckBox

    val room by lazy {
        Room.databaseBuilder(
            requireContext(), TMDBDatabase::class.java, "popular_movies"
        ).build()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val view = binding.root


        initRecyclerview()
        loadData()

        Log.d(ContentValues.TAG, "EYYYYYYY2")

        return view

    }

    override fun onResume() {
        super.onResume()

        initRecyclerview()
        loadData()
        recyclerViewAdapter.onItemClick = {

            var movieList = listOf<Movie>()
            lifecycleScope.launch {

                room.getMovieDao().saveMovies(it)
                //movieList = room.getMovieDao().getAll()

                //Toast.makeText(context,"${movieList}", Toast.LENGTH_LONG).show()
            }

            sharedViewModel.saveId(it.id)
            sharedViewModel.saveMovie(it)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
        Log.d(ContentValues.TAG, "OOOONNN RESUMEEEEEEEEEEEEEEE")


    }

    private fun initRecyclerview() {

        lifecycleScope.launch {

            recyclerViewAdapter = RecyclerViewAdapter(sharedViewModel)

            binding.recyclerView.apply {
                adapter = recyclerViewAdapter
                layoutManager = LinearLayoutManager(this@MovieFragment.context)
                val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                addItemDecoration(decoration)
                setHasFixedSize(true)
                //displayPopularMovies()
                /*
                adapter = RecyclerViewAdapter().apply {
                    setData((sharedViewModel.movieFavList))
                }*/

            }
        }


    }


    private fun loadData() {
        lifecycleScope.launch {
            viewModel.getAllMovies(requireContext()).collect {
                recyclerViewAdapter.submitData(it)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        recyclerViewAdapter.onItemClick = {

            var movieList = listOf<Movie>()
            lifecycleScope.launch {

                room.getMovieDao().saveMovies(it)
                //movieList = room.getMovieDao().getAll()

                //Toast.makeText(context,"${movieList}", Toast.LENGTH_LONG).show()
            }

            sharedViewModel.saveId(it.id)
            sharedViewModel.saveMovie(it)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
        Log.d(ContentValues.TAG, "EYYYYYYY1")
    }

}


