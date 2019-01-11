package com.rescat.rescat_android.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.rescat.rescat_android.ui.fragment.helpcat.adopt.MedicalCommentFragment
import com.rescat.rescat_android.ui.fragment.helpcat.adopt.MedicalInfoFragment


class MedicalTabAdapter(fm: FragmentManager, val idx:Int) : FragmentStatePagerAdapter(fm){

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment? {
        val medicalInfoFragment = MedicalInfoFragment.newInstance(idx)
        val medicalcommentFragment = MedicalCommentFragment.newInstance(idx)
        return when (position) {
            0 -> medicalInfoFragment
            1 -> medicalcommentFragment
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