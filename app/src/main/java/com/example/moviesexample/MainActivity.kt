package com.example.moviesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.NavController
import com.example.moviesexample.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inicializar()
    }

    /*
    fun inicializar(){
        val adapter = TabsFragmentAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        adapter.addItem(MovieFragment(), "Movies")
        adapter.addItem(FavFragment(), "Favourites")

        val viewPager = binding.viewPager
        viewPager.adapter = adapter

        val tabLayout = binding.tabs
        tabLayout.setupWithViewPager(viewPager)
    }

    class TabsFragmentAdapter(fm: FragmentManager, behavior:Int) :
            FragmentPagerAdapter(fm, behavior){

        private val listFragment: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        fun addItem(fragment: Fragment, titulo: String){
            listFragment.add(fragment)
            titleList.add(titulo)
        }

        override fun getCount(): Int {
            return listFragment.size
        }

        override fun getItem(position: Int): Fragment {
            return listFragment[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
     */
}