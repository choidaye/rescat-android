package com.rescat.rescat_android.ui.fragment.helpcat.adopt

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.rescat.rescat_android.R

class MedicalCommentFragment : Fragment() {

    var idx: Int = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_support_comment, container, false)
    }


    companion object {
        fun newInstance(idx: Int): AdoptCommentFragment {
            val args = Bundle()
            args.putInt("idx", idx)
            val fragment = AdoptCommentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}
