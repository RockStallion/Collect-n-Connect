package com.collect.connect.FireBase

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.collect.connect.Collections
import com.collect.connect.Principal

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllSetsFragment()
            1 -> ProfileSetsFragment()
            else -> AllSetsFragment()
        }
    }

}