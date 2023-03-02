package com.example.moviesexample.adapter

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviesexample.FavFragment
import com.example.moviesexample.MovieFragment
import com.example.moviesexample.db.TMDBDatabase
import kotlinx.coroutines.launch

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {



    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
            0->{
                MovieFragment()
            }
            1 ->{
                FavFragment()
            }
            else -> {
                Fragment()
            }
        }
    }



}