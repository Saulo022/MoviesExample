package com.example.moviesexample

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.moviesexample.adapter.LoaderStateAdapter
import com.example.moviesexample.adapter.RecyclerViewAdapter
import com.example.moviesexample.data.Movie
import com.example.moviesexample.data.MovieList
import com.example.moviesexample.databinding.FragmentMovieBinding
import com.example.moviesexample.databinding.RecyclerListRowBinding
import com.example.moviesexample.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import hilt_aggregated_deps._dagger_hilt_android_internal_modules_ApplicationContextModule
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding:FragmentMovieBinding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()
    private val sharedViewModel : MovieViewModel by activityViewModels()

    private lateinit var recyclerViewAdapter : RecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val view = binding.root

        initRecyclerview()
        loadData()
        return view
    }

    private fun initRecyclerview() {
        binding.apply {
            recyclerViewAdapter = RecyclerViewAdapter()

            recyclerView.apply {
                adapter = recyclerViewAdapter
                layoutManager = LinearLayoutManager(this@MovieFragment.context)
                val decoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
                addItemDecoration(decoration)
                setHasFixedSize(true)

            }
        }
    }

    private fun loadData(){
        lifecycleScope.launch {
            viewModel.getAllMovies.collect{
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter.onItemClick = {
            Toast.makeText(context, "id" + it.id, Toast.LENGTH_SHORT).show()
            sharedViewModel.saveId(it.id)
            sharedViewModel.saveMovie(it)
            findNavController().navigate(R.id.action_movieFragment_to_detailFragment)

        }

    }
}