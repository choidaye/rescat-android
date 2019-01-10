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
import com.rescat.rescat_android.Get.GetMapResponse
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.model.SearchKeywordData
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.adapter.SearchKeywordRecyclerViewAdapter
import kotlinx.android.synthetic.main.rv_item_search_keyword.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast


class SearchActivity : AppCompatActivity() {


    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }



    lateinit var searchRecyclerViewAdapter: SearchRecyclerViewAdapter
    lateinit var searchKeywordRecyclerViewAdapter: SearchKeywordRecyclerViewAdapter

    lateinit var searchList : ArrayList<SearchData>

    val searchKeywordList : ArrayList<SearchKeywordData> by lazy{

        ArrayList<SearchKeywordData>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setRecyclerView()
        setOnBtnClickListener()
        setOnEditorActionListener()
        setRecyclerViewSearch()

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
        li_ac_search_result.setVisibility(View.VISIBLE)

    }

    private fun setOnBtnClickListener() {
        btn_ac_search_searchbtn.setOnClickListener{

            //에디트텍스트 값 잘받아오나 테스트
            toast(et_ac_search_text.text)
            li_ac_search_keyword.setVisibility(View.GONE)
            li_ac_search_result.setVisibility(View.VISIBLE)


//            if (et_ac_search_text.text.toString().isNotEmpty()){
//
//                val position : Int = searchKeywordRecyclerViewAdapter.searchKeywordList.size
//                val keyword: String = tv_ac_search_keyword.text.toString()
//                val searchKeywordData : SearchKeywordData = SearchKeywordData(keyword)
//                searchKeywordRecyclerViewAdapter.searchKeywordList.add(searchKeywordData)
//                searchKeywordRecyclerViewAdapter.notifyItemInserted(position)          // 특정 데이터가 바꼈을 때
//
//            }
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


    private fun setRecyclerViewSearch(){

        searchKeywordRecyclerViewAdapter = SearchKeywordRecyclerViewAdapter(this, searchKeywordList)               //by lazy로 인해 이때부터 메모리 할당을 한다.
        rv_ac_search_keyword_list.adapter = searchKeywordRecyclerViewAdapter
        rv_ac_search_keyword_list.layoutManager = LinearLayoutManager(this)

    }


}
