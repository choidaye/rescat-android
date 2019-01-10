package com.rescat.rescat_android.ui.fragment.helpcat.adopt

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
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
import com.rescat.rescat_android.Post.PostCareComment
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.model.CommentData
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.adapter.CommentRecyclerViewAdapter
import com.rescat.rescat_android.utils.ErrorBodyConverter
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.fragment_adopt_comment.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProtectCommentFragment : Fragment() {
    lateinit var commentRecyclerViewAdapter: CommentRecyclerViewAdapter
    var idx: Int = 0
    var TEST_TOKEN:String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJSeWFuZ1QiLCJ1c2VyX2lkeCI6MSwiZXhwIjoxNTQ4NzU2OTEwfQ.XC0S5ywa4DYU4JYxenSio-4q7Pn-SDVyv0MY4S-_IeM"

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
        setButtonListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adopt_comment, container, false)
    }

    private fun setButtonChange() {
        edit_adopt_comment.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(edit_adopt_comment.text.isEmpty()) {
                    btn_adopt_comment_send.setImageResource(R.drawable.ic_message_off)
                    btn_adopt_comment_send.isClickable = false
                } else {
                    btn_adopt_comment_send.setImageResource(R.drawable.ic_message_on)
                    btn_adopt_comment_send.isClickable = true
                    setButtonListener()
                }
            }
        })
    }

    private fun InitRecyclerView() {
        val data :ArrayList<CommentData> = ArrayList()
        commentRecyclerViewAdapter = CommentRecyclerViewAdapter(data) {
            showBottomSheetDialog(it)
        }
        rv_help_cat_comment.adapter = commentRecyclerViewAdapter
        rv_help_cat_comment.layoutManager = LinearLayoutManager(activity)
        context?.let {
            val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)

            ContextCompat.getDrawable(it, R.drawable.line_divider)?.let {
                itemDecoration.setDrawable(it)
                rv_help_cat_comment.addItemDecoration(itemDecoration)
            }
        }
        getCommentData()
    }


    private fun getCommentData() {
        val getCarePostComment: Call<ArrayList<CommentData>> =
            networkService.getCarePostComment(idx)

        getCarePostComment.enqueue(object: Callback<ArrayList<CommentData>> {
            override fun onFailure(call: Call<ArrayList<CommentData>>, t: Throwable) {
                Log.e("Comment Fail", t.toString())
            }

            override fun onResponse(call: Call<ArrayList<CommentData>>, response: Response<ArrayList<CommentData>>) {
                if(response.isSuccessful) {
                    commentRecyclerViewAdapter.dataList = response.body()!!
                    commentRecyclerViewAdapter.notifyDataSetChanged()
                }
            }
        })
    }


    private fun setButtonListener() {
        btn_adopt_comment_send.setOnClickListener {
            //TODO. 예외처리하기!
            val postCommentData: PostCareComment = PostCareComment(edit_adopt_comment.text.toString(), idx)
            val postCareComment: Call<CommentData> =
                networkService.postCarePostComment(TEST_TOKEN, postCommentData, idx)

            postCareComment.enqueue(object: Callback<CommentData> {
                override fun onFailure(call: Call<CommentData>, t: Throwable) {
                    Log.e("Sign Up Fail", t.toString())
                }

                override fun onResponse(call: Call<CommentData>, response: Response<CommentData>) {
                    if(response.isSuccessful) {
                        edit_adopt_comment.text.clear()
                        edit_adopt_comment.hideKeyboard()
                        Toast.makeText(activity!!, "댓글이 등록되었습니다", Toast.LENGTH_SHORT).show()
                        getCommentData()
                    }
                }
            })
        }
    }

    private fun showBottomSheetDialog(commentIndex:Int) {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(activity!!)
        dialog.setContentView(view)
        view.text_bottom_sheet_delete.setOnClickListener {
            val deleteCareComment: Call<ResponseBody> =
                networkService.deleteCarePostComment(TEST_TOKEN, idx, commentIndex)

            deleteCareComment.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Delete Comment Fail", t.toString())
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful) {
                        Toast.makeText(activity, "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        //TODO. get Error Message
                        val errorMessage = ErrorBodyConverter.convert(response.errorBody()!!)
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }
            })
        }

        view.text_bottom_sheet_report.setOnClickListener {
            val reportCareComment: Call<ResponseBody> =
                networkService.reportCarePostComment(TEST_TOKEN, idx, commentIndex)

            reportCareComment.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("report Comment Fail", t.toString())
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful) {
                        Toast.makeText(activity, "해당 댓글을 신고했습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorMessage = ErrorBodyConverter.convert(response.errorBody()!!)
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }
            })
        }
        dialog.show()
    }

    companion object {
        fun newInstance(idx: Int): ProtectCommentFragment {
            val args = Bundle()
            args.putInt("idx", idx)
            val fragment = ProtectCommentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
