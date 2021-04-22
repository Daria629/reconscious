package com.medical.reconscious.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.medical.reconscious.R
import com.medical.reconscious.adapters.FeelingSelectRecyclerAdapter
import com.medical.reconscious.adapters.OnFeelingItemClickListener
import com.medical.reconscious.network.APIService
import com.medical.reconscious.network.ServiceBuilder
import com.medical.reconscious.network.respones.JournalResponse
import com.medical.reconscious.prefs
import com.medical.reconscious.utils.*
import kotlinx.android.synthetic.main.activity_feeling_select.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FeelingSelectActivity: BaseActivity(), View.OnClickListener, OnFeelingItemClickListener {

    private val request = ServiceBuilder.buildService(APIService::class.java)
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: FeelingSelectRecyclerAdapter

    var feelingList = listOf("Amazing", "Good", "Content", "So so…", "Bad", "Awful")
    var feelingIconList = listOf(R.drawable.icon_feel_amazing, R.drawable.icon_feel_good, R.drawable.icon_feel_content, R.drawable.icon_feel_soso, R.drawable.icon_feel_bad, R.drawable.icon_feel_awful)

    var indexFeeling = -1

    var tag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeling_select)

        tag = intent.getStringExtra("tag").toString()

        layout_skip.setOnClickListener(this)
        layout_next.setOnClickListener(this)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = linearLayoutManager

        adapter = FeelingSelectRecyclerAdapter(feelingList, feelingIconList, this)
        recyclerview.adapter = adapter

        heightAnimation(layout_next, 48.px, 0, 0)
    }

    fun onCreateJournal() {

        val jsonObject = JSONObject()

        jsonObject.put(APIParams.FEELING, filterStringWithoutSpace(feelingList[indexFeeling]).toLowerCase(Locale.ROOT))
        jsonObject.put(APIParams.IS_LOCKED, true)
        jsonObject.put(APIParams.TITLE, "")
        jsonObject.put(APIParams.BODY, "")
//        jsonObject.put(APIParams.APPOINTMENT, 0)

        showHUD()

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val response = request.createJournal(requestBody, "Bearer " + prefs.token!!)
        response.enqueue(object : Callback<JournalResponse> {

            override fun onResponse(call: Call<JournalResponse>, response: Response<JournalResponse>) {
                hideHUD()
                if (response.body() != null) {
                    finish()
                    overridePendingTransition(R.anim.top_finish_out, R.anim.top_finish_in)
                } else if (response.errorBody() != null) {
                    showError(this@FeelingSelectActivity, response.errorBody()!!.string())
                } else {
                    Toast.makeText(this@FeelingSelectActivity, "server error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<JournalResponse>, t: Throwable) {
                hideHUD()
                Toast.makeText(this@FeelingSelectActivity,"${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClicked(position: Int, isSelected: Boolean) {

        indexFeeling = position

        if (isSelected && layout_next.measuredHeight == 0) heightAnimation(layout_next, 0, 48.px, 200)
        else if (!isSelected && layout_next.measuredHeight != 0) heightAnimation(layout_next, 48.px, 0, 200)

        adapter.setSelection(position)
    }

    override fun onClick(v: View?) {
        if (v == layout_skip) {
            finish()
            overridePendingTransition(R.anim.top_finish_out, R.anim.top_finish_in)
        } else if (v == layout_next) {

            if (tag == JournalDetailActivity.TAG) {
                setResult(JournalDetailActivity.REQUEST_CODE, Intent().putExtra("tag", feelingList[indexFeeling]))
                finish()
                overridePendingTransition(R.anim.top_finish_out, R.anim.top_finish_in)
            } else {
                onCreateJournal()
            }

        }
    }
}