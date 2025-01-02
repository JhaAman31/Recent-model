package com.example.recentsmodule

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.messaging.Users.presentation.fragments.UserFragment
import com.example.notification.NotificationFragment


class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserFragment()
            1 -> NotificationFragment()
//            2 -> ProfileFragment()
            else -> UserFragment()
        }
    }

}
