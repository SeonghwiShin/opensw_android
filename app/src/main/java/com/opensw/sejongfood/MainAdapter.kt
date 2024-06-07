package com.opensw.sejongfood

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainAdapter(
    fragment: FragmentActivity
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> MainFragment()
            1 -> WishFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
}
