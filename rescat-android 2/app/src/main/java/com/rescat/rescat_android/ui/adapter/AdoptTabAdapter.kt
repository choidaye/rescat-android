package com.rescat.rescat_android.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.rescat.rescat_android.ui.fragment.helpcat.adopt.AdoptCommentFragment
import com.rescat.rescat_android.ui.fragment.helpcat.adopt.AdoptInfoFragment

class AdoptTabAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> AdoptInfoFragment()
            1 -> AdoptCommentFragment()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "상세정보"
            1 -> "댓글"
            else -> null
        }
    }
}