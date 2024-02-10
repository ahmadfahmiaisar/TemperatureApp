package com.smadu1.temperatureapp.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.smadu1.temperatureapp.HistoryFragment
import com.smadu1.temperatureapp.InformationFragment
import com.smadu1.temperatureapp.menu.task.TaskFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 3 // because this is fix number

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TaskFragment.newInstance()
            1 -> InformationFragment.newInstance()
            2 -> HistoryFragment.newInstance()
            else -> TaskFragment.newInstance()
        }
    }
}