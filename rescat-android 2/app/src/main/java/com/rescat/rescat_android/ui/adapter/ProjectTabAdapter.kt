package com.rescat.rescat_android.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.rescat.rescat_android.ui.fragment.helpcat.Project.ProjectCommentFragment
import com.rescat.rescat_android.ui.fragment.helpcat.Project.ProjectInfoFragment


class ProjectTabAdapter(fm: FragmentManager, val idx:Int): FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int = 2


    override fun getItem(position: Int): Fragment? {
        val projectInfoFragment = ProjectInfoFragment.newInstance(idx)
        val projectcommentFragment = ProjectCommentFragment.newInstance(idx)
        return when (position) {
            0 -> projectInfoFragment
            1 -> projectcommentFragment
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