package com.rescat.rescat_android.ui.fragment.helpcat.Project

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.rescat.rescat_android.Get.GetFundingCommentResponse
import com.rescat.rescat_android.Post.PostCareComment
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.model.CommentData
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.adapter.SupportCommentRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_adopt_comment.*
import kotlinx.android.synthetic.main.fragment_support_comment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class  ProjectCommentFragment : Fragment() {

    lateinit var supportCommentRecyclerViewAdapter: SupportCommentRecyclerViewAdapter
    var idx: Int = 0
    var present : Boolean = true


    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idx = arguments!!.getInt("idx")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        InitRecyclerView()
        setButtonChange()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_support_comment, container, false)
    }

    private fun setButtonChange() {
        edit_support_comment.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(edit_adopt_comment.text.isEmpty()) {
                    btn_support_comment_send.setImageResource(R.drawable.ic_message_off)
                    btn_support_comment_send.isClickable = false
                } else {
                    btn_support_comment_send.setImageResource(R.drawable.ic_message_on)
                    btn_support_comment_send.isClickable = true
                    setButtonListener()
                }
            }
        })
    }

    private fun InitRecyclerView() {
        val data :ArrayList<GetFundingCommentResponse> = ArrayList()
        supportCommentRecyclerViewAdapter = SupportCommentRecyclerViewAdapter(data)
        rv_support_comment.adapter = supportCommentRecyclerViewAdapter
        rv_support_comment.layoutManager = LinearLayoutManager(activity)
        context?.let {
            val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)

            ContextCompat.getDrawable(it, R.drawable.line_divider)?.let {
                itemDecoration.setDrawable(it)
                rv_support_comment.addItemDecoration(itemDecoration)
            }
        }
        getFundingCommentData()
    }


    private fun getFundingCommentData() {
        val getFundingComment: Call<ArrayList<GetFundingCommentResponse>> =
            networkService.getFundingComment(idx, present)

        getFundingComment.enqueue(object: Callback<ArrayList<GetFundingCommentResponse>> {
            override fun onFailure(call: Call<ArrayList<GetFundingCommentResponse>>, t: Throwable) {
                Log.e("Comment Fail", t.toString())
            }

            override fun onResponse(call: Call<ArrayList<GetFundingCommentResponse>>, response: Response<ArrayList<GetFundingCommentResponse>>) {
                if(response.isSuccessful) {
                    supportCommentRecyclerViewAdapter.dataList = response.body()!!
                    supportCommentRecyclerViewAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun setButtonListener() {
        btn_support_comment_send.setOnClickListener {
            //TODO. 예외처리하기!
            val postCommentData: PostCareComment = PostCareComment(edit_adopt_comment.text.toString(), idx)
            val postCareComment: Call<CommentData> =
                networkService.postCarePostComment(postCommentData, idx)

            postCareComment.enqueue(object: Callback<CommentData> {
                override fun onFailure(call: Call<CommentData>, t: Throwable) {
                    Log.e("Sign Up Fail", t.toString())
                }

                override fun onResponse(call: Call<CommentData>, response: Response<CommentData>) {
                    if(response.isSuccessful) {
                        edit_support_comment.text.clear()
                        edit_support_comment.hideKeyboard()
                        Toast.makeText(activity!!, "댓글이 등록되었습니다", Toast.LENGTH_SHORT).show()
                        getFundingCommentData()
                    }
                }
            })
        }
    }

    companion object {
        fun newInstance(idx: Int): ProjectCommentFragment {
            val args = Bundle()
            args.putInt("idx", idx)
            val fragment = ProjectCommentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
