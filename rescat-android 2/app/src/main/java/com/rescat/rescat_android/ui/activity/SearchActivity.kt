package com.rescat.rescat_android.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.rescat.rescat_android.R
import com.rescat.rescat_android.R.id.*
import com.rescat.rescat_android.model.SearchData
import com.rescat.rescat_android.ui.adapter.SearchRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_search.*
import android.widget.TextView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast


class SearchActivity : AppCompatActivity() {


    lateinit var searchRecyclerViewAdapter: SearchRecyclerViewAdapter

    val dataList : ArrayList<SearchData> by lazy {
        ArrayList<SearchData>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setRecyclerView()
        setOnBtnClickListener()
        setOnEditorActionListener()

    }


    fun setOnEditorActionListener() {
        et_ac_search_text.setOnEditorActionListener (object : TextView.OnEditorActionListener {
            override fun onEditorAction(et_ac_search_text: TextView, actionId: Int, event: KeyEvent): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch()
                    //에디트텍스트 값 잘받아와지나 테스트
                    toast(et_ac_search_text.text)
                    return true
                }
                return false
            }
        })
    }

    private fun performSearch() {
        setRecyclerView()
        li_ac_search_keyword.setVisibility(View.GONE)
        rv_ac_search_list.setVisibility(View.VISIBLE)



    }

    private fun setOnBtnClickListener() {
        btn_ac_search_searchbtn.setOnClickListener{
            li_ac_search_keyword.setVisibility(View.GONE)
            li_ac_search_result.setVisibility(View.VISIBLE)

            //에디트텍스트 값 잘받아오나 테스트
            toast(et_ac_search_text.text)
        }

        goresult.setOnClickListener {

            startActivity<SearchResultActivity>()

        }
    }

    private fun setRecyclerView() {

        var dataList: ArrayList<SearchData> = ArrayList()
        dataList.add(SearchData(1,"제목","헬로우","카테고리",1,"사진",true,true))
        dataList.add(SearchData(1,"제목","헬로우","카테고리",1,"사진",true,true))
        dataList.add(SearchData(1,"제목","헬로우","카테고리",1,"사진",true,true))
        dataList.add(SearchData(1,"제목","헬로우","카테고리",1,"사진",true,true))
        dataList.add(SearchData(1,"제목","헬로우","카테고리",1,"사진",true,true))
       searchRecyclerViewAdapter = SearchRecyclerViewAdapter(this, dataList)
       rv_ac_search_list.adapter = searchRecyclerViewAdapter
       rv_ac_search_list.layoutManager = LinearLayoutManager(this)
    }


}
