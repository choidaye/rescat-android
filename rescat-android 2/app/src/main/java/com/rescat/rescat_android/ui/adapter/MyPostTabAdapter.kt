package com.rescat.rescat_android.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.rescat.rescat_android.ui.fragment.mypage.mypost.MyPostCareFragment
import com.rescat.rescat_android.ui.fragment.mypage.mypost.MyPostFundFragment

class MyPostTabAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> MyPostCareFragment()
            1 -> MyPostFundFragment()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "입양/임시보호"
            1 -> "펀딩"
            else -> null
        }
    }
}