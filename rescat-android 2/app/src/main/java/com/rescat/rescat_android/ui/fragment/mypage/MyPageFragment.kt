package com.rescat.rescat_android.ui.fragment.mypage

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.activity.sign.SignUpActivity
import kotlinx.android.synthetic.main.fragment_my_page.*

class MyPageFragment : Fragment() {

    val requestCode = 2009


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnCLickListener()
    }

    private fun setOnCLickListener() {
        btn_fg_my_page_sign_up.setOnClickListener {
            activity!!.startActivityForResult(Intent(activity, SignUpActivity::class.java),requestCode)
        }

    }


//    fun setupMypageFragment(){
//        val fragment = HelpCatFragment()
//        addFragment(fragment)
//    }



}
