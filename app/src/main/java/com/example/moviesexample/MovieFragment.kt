package com.example.moviesexample

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesexample.adapter.RecyclerViewAdapter
import com.example.moviesexample.data.Movie
import com.example.moviesexample.data.MovieList
import com.example.moviesexample.databinding.FragmentMovieBinding
import com.example.moviesexample.viewModel.MovieViewModel



class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding:FragmentMovieBinding get() = _binding!!

    private val sharedViewModel : MovieViewModel by activityViewModels()

    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var recyclerAdapter : RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie, container, false)

        initViewModel(view)
        initViewModel()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter.onItemClick = {

            Toast.makeText(context, "id" + it.id, Toast.LENGTH_SHORT).show()
            sharedViewModel.saveId(it.id)
            sharedViewModel.saveMovie(it)
            findNavController().navigate(R.id.action_movieFragment_to_detailFragment)

        }
    }

    private fun initViewModel(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)

        recyclerAdapter = RecyclerViewAdapter(emptyList<Movie>()){}
        recyclerView.adapter = recyclerAdapter

    }

    private fun initViewModel(){
        val viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        viewModel.getRecyclerListObserver().observe(viewLifecycleOwner, Observer<MovieList>{
            if(it !=null){
                recyclerAdapter.setUpdatedData(it.results)
            } else {
                Toast.makeText(activity, "Error in getting data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall()
    }
}