package com.rescat.rescat_android.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.fragment.catmap.CatMapFragment
import com.rescat.rescat_android.ui.fragment.helpcat.HelpCatFragment
import com.rescat.rescat_android.ui.fragment.mypage.MyPageFragment
import com.rescat.rescat_android.ui.fragment.mypage.MyPageMemberFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavi()

        setOnBtnClickListener()


    }

    private fun setOnBtnClickListener() {

    }

    private fun setupBottomNavi() {
        main_bottom_navi.menu.getItem(1).isChecked = true

        val fragment = HelpCatFragment()
        addFragment(fragment)

        main_bottom_navi.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuitem_catmap -> {
                    val fragment = CatMapFragment()
                    addFragment(fragment)
                    it.isChecked = true
                    true
                }
                R.id.menuitem_helpcat -> {
                    val fragment = HelpCatFragment()
                    addFragment(fragment)
                    it.isChecked = true
                    true
                }
                R.id.menuitem_mypage -> {
                    val fragment = MyPageMemberFragment()
                    addFragment(fragment)
                    it.isChecked = true
                    true
                }
            }
            false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2009){
            Log.v("ygygygy", "gygygyg22")
            addFragment(MyPageMemberFragment())

        }
    }

//    fun addFragment2(fragment: Fragment) {
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.mypage_fragment_container, fragment, fragment.javaClass.simpleName)
//            .addToBackStack(fragment.javaClass.simpleName)
//            .commit()
//    }


    fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}
