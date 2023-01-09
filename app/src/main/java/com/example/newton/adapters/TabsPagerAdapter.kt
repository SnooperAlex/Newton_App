package com.example.newton.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newton.ForYouFragment
import com.example.newton.SearchFragment
import com.example.newton.BreakingFragment

class TabAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(index: Int): Fragment {
        when (index) {
            0 -> return ForYouFragment()
            1 -> return BreakingFragment()
            2 -> return SearchFragment()
        }
        return BreakingFragment()
    }

    // get item count - equal to number of tabs
    override fun getItemCount(): Int
    {
        return 3
    }
}