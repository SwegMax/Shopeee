package com.example.shopeee.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shopeee.R
import com.example.shopeee.adapter.HomeViewpagerAdapter
import com.example.shopeee.databinding.FragmentHomeBinding
import com.example.shopeee.views.categories.BurgerFragment
import com.example.shopeee.views.categories.DrinksFragment
import com.example.shopeee.views.categories.MainCategoryFragment
import com.example.shopeee.views.categories.RamenFragment
import com.example.shopeee.views.categories.SnacksFragment
import com.example.shopeee.views.categories.SupplementsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragments = arrayListOf (
                MainCategoryFragment(),
                SnacksFragment(),
                DrinksFragment(),
                SupplementsFragment(),
                RamenFragment(),
                BurgerFragment()
        )

        binding.viewpagerHome.isUserInputEnabled = false

        val viewPager2Adapter =
            HomeViewpagerAdapter(categoriesFragments, childFragmentManager, lifecycle)
        binding.viewpagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpagerHome) {tab, position ->
            when (position) {
                0 -> tab.text = "Main"
                1 -> tab.text = "Snacks"
                2 -> tab.text = "Drinks"
                3 -> tab.text = "Supplements"
                4 -> tab.text = "Ramen"
                5 -> tab.text = "Burger"
            }
        }.attach()
    }
}