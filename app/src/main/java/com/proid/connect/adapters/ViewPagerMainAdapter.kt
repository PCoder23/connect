package com.proid.connect.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.proid.connect.fragments.ChatFragment
import com.proid.connect.fragments.MeetFragment

class ViewPagerMainAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment {
        return if (position==0){
            ChatFragment()
        }else{
            MeetFragment()
        }
    }

}